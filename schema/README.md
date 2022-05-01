# Schema

This is the module the schema and a CLI to test the generation of the schema inside OrientDB.

## Running it as standalone

If you need to test the schema CLI, you can follow those instructions.

### Start OrientDB

> docker run -p -d 2424:2424 -p 2480:2480 -e ORIENTDB_ROOT_PASSWORD=dev_password65 --name orientdb-products orientdb

The OrientDB UI will be available at http://localhost:2480/studio/index.html.

The URL is also visible in the logs.

### Run the CLI

I am running it in the IDE directly, so I will indicate which arguments to use.

If needed, your can use --help of the CLI.

1. Generate the schema

> generate-orientdb-schema --server-password "dev_password65"

2. Generate the data

You need to adapt the folder path to your actual data folder.

The data need to be in [TOML](https://toml.io/en/) format.

> generate-orientdb-data --server-password "dev_password65" --folder-path "../products-data"

3. Generate the schema JSON

This command line can generate a [schema JSON](https://json-schema.org/) so that it will easier to add data items.

I am using this extension [Better Stronger TOML](https://github.com/isirode/better-stronger-toml), that I made, for that.

### Cleanup

If you need to clean up the OrientDB server, you can run:

Do not use those commands on your production server.

This will delete the schema, but the data will remain.

> generate-orientdb-schema --server-password "dev_password65" --direction DOWN

This will delete the schema and the data.

> generate-orientdb-schema --server-password "dev_password65" --direction HARDDOWN

