package lodgingCommander.entity;

import com.fasterxml.jackson.annotation.*;
import com.hotel.lodgingCommander.entity.Cart;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.Img;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id", nullable = true)
    @JsonBackReference
    private Img img;

    @Column(nullable = false)
    private int quantity;

    @Lob
    private String detail;

    @Column(name = "max_people", nullable = false)
    private int maxPeople;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Cart> carts;
}

