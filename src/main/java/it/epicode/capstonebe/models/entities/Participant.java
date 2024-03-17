package it.epicode.capstonebe.models.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Participant {

    private String              name;
    private String              surname;
    private String              email;
    private String              phone;
    private LocalDate           birthday;
    private String              address;
    private String              nationality;  //TODO add csv with nationalities
    private String              allergies;    //TODO add list of allergies

}
