package com.nancologist.car360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", columnDefinition = "VARCHAR(10)", unique = true)
    private String code;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    // @Lob => normalerweise es soll funktionieren aber in Postgres fuer BYTEA funktioniert es nicht... und es verursacht folgende Fehlermeldung:
    // "Cannot convert the column of type BYTEA to requested type long"
    /*
        A useful explanation from https://stackoverflow.com/questions/68851570/hibernate-lob-on-byte-causes-bad-value-for-type-long

        The bytea type is inlined into the table whereas other types are chunked into a separate table which is called TOAST on PostgreSQL.
        To access these values, database have a concept often referred to as a LOB locator which essentially is just an id for doing the lookup.
        Some drivers/databases just work either way but others might need to match the actual physical representation.
        In your case, using @Lob is just wrong because AFAIK bytea is inlined up to a certain size and de-TOASTed i.e. materialized automatically behind the scenes if necessary.
        If you were using the varbinary/blob type or something like that, you would have to use @Lob as in that case, the main table only contains this LOB locator which is a long.
        The driver then knows when you ask for the value by using getBlob that it has to execute some select get_lob(?) query to retrieve the actual contents.
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] imageBytes;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getName() {
        return name;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
