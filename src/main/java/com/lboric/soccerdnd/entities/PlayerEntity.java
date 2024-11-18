package com.lboric.soccerdnd.entities;

import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.lboric.soccerdnd.models.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a player in the database.
 *
 * <p>
 * This entity is used to map the player data to the corresponding database table, including the player's ID,
 * name, surname, and their related statistics.
 * </p>
 *
 * <p>
 * The player entity maintains a one-to-many relationship with the {@link PlayerStatsEntity} representing
 * the statistics of the player for different seasons.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "surname"}))
public class PlayerEntity {

    /**
     * The unique identifier for the player.
     * This field is generated automatically and serves as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the player.
     * This field is required and cannot be {@code null}.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The surname (last name) of the player.
     * This field is required and cannot be {@code null}.
     */
    @Column(name = "surname", nullable = false)
    private String surname;

    /**
     * A collection of player statistics related to this player.
     * This relationship is mapped to the {@link PlayerStatsEntity} and cascades deletions.
     */
    @OneToMany(mappedBy = "player")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PlayerStatsEntity> playerStats;

    /**
     * Converts this {@link PlayerEntity} to a {@link Player} model object.
     *
     * @return a {@link Player} object containing the data from this entity
     */
    public Player toModel() {
        return Player.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
