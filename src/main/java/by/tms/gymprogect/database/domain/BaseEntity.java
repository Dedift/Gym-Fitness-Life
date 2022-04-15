package by.tms.gymprogect.database.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity <PK extends Serializable>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected PK id;
}
