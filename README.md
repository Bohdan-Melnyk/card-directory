How to run the project.

1. Run command: `mvn spring-boot:run`.
2. Follow to the link for UI: `http://localhost:8080/index.html`

All table will create automatically from schema.sql by path `src/main/resources/schema`.

To avoid downtime, I create two tables:

`cards`: the main table used by the service to access data.

`cards_temp`: temporary table for loading new data.

I load new data into the `cards_temp` temporary table.
After the successful completion of the download, I replace the tables (renaming).

I use Spring's `@Scheduled` annotation to make request every hour.