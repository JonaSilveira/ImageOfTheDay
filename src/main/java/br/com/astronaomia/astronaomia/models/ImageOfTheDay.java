package br.com.astronaomia.astronaomia.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@ToString
public class ImageOfTheDay{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Exclude private String id;

    @Column
    private String date;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column
    private String copyright;

    @Column
    @SerializedName(value="hdurl")
    private String urlHD;

    @SerializedName(value="media_type")
    @Column
    private String mediaType;

    @Column
    private String title;

    @Column
    private String url;

    public ImageOfTheDay() {
        this.id = UUID.randomUUID().toString();
    }

    @Column(columnDefinition = "TEXT")
    private String explanationPt;

    @Column
    private String titlePt;




}
