package by.tms.gymprogect.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseDTO <PK extends Serializable>{

    protected PK id;
}
