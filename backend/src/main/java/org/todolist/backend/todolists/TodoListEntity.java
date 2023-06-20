package org.todolist.backend.todolists;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.todolist.backend.security.user.User;

import java.time.ZonedDateTime;

@Entity
@Table(name = "todo_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_generator")
    @SequenceGenerator(
            name = "todo_generator",
            schema = "public",
            sequenceName = "public.todo_id_seq",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private ZonedDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
