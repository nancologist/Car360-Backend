# Project Car360
Es handelt sich um ein inoffiziell seltenes BMW Model (F11 550i LCI). Laut Angaben mit Stueckzahl von 180.

## Aufgaben

### From Hibernate auto-update to Liquibase

As we have already started and created some tables with Hibernate auto-update, we should tell the liquibase marke these parts "already as applied":

```
liquibase changelogSync \
--url=jdbc:postgresql://localhost:5432/springular \
--username=admin \
--password=admin \
--defaultSchemaName=car360 \
--changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml
```

This creates the two tracking tables:

`car360.databasechangelog`

`car360.databasechangeloglock`

And marks all current changes in the empty changelog as “already applied.”
Now your DB is registered with Liquibase, without modifying existing schema.

2. In future if you update any Entity , you shuold crate a changelog for that:

```
liquibase diffChangeLog \
  --referenceUrl=hibernate:spring:com.nancologist.car360 \
  --url=jdbc:postgresql://localhost:5432/<db-name> \
  --username=admin \
  --password=admin \
  --defaultSchemaName=<schema-name> \
  --changeLogFile=src/main/resources/db/changelog/<new-changeset>.yaml

```

### Weiteres
* [ ] (?) Erstellung von einer Lookuptabelle fuer Fahrzeug eigenschaften.