package org.todolist.backend.todolists;

import jakarta.persistence.*;
import lombok.*;
import org.todolist.backend.security.user.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "todo_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private LocalDateTime deadline;

    @Column(name = "finished")
    private boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
