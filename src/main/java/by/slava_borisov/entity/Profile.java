package by.slava_borisov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false, length = 25)
    private String phone;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
}