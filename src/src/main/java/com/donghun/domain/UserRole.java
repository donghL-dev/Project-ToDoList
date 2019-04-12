
package com.donghun.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author dongh9508
 * @since  2019-03-29
 */
@Entity
@Table
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "rno")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String roleName;
}