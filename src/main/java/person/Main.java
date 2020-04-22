package person;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import legoset.model.LegoSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;

public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

    private static Person randomPerson() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        Date date = faker.date().birthday();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Person.Gender gender = faker.options().option(Person.Gender.class);
        String email = faker.internet().emailAddress();
        String profession = faker.company().profession();

        String country = faker.address().country();
        String state = faker.address().state();
        String city = faker.address().city();
        String streetAddress = faker.address().streetAddress();
        String zip = faker.address().zipCode();
        Person rPerson = Person.builder()
                .name(name)
                .dob(localDate)
                .gender(gender)
                .address(new Address(country, state, city, streetAddress, zip))
                .email(email)
                .profession(profession)
                .build();
        return rPerson;
    }

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < 1000; i++) {
                em.persist(randomPerson());
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
