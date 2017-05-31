package com.alb.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Created by Альберт on 24.05.2017.
 */

@Entity
@ToString
public class Account implements Serializable {
    @Id
    @GeneratedValue
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String login;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private boolean admin;
}