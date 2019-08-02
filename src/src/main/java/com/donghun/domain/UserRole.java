
package com.donghun.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author dongh9508
 * @since 2019-03-29
 */
@Entity
@Table
@Data
@EqualsAndHashCode(of = "rno")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String roleName;
}