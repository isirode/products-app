# Products app

This project allow to search products (mostly software products for now such as languages, libraries etc) based on their properties.

For instance, we can do:

```sql
Select * From Library Where Library.properties.madeIn = 'Java'
```

The website (work in progress) is available here : https://web.products.onesime-deleham.ovh

## Modules

The project is composed of multiple sub-modules:
* [schema](schema/README.md) : contains the definition of the schema and utilities to setting it up in OrientDB
* [api](api/README.md) : contains the api serving the data
* [web](web/README.md) : contains the site web displaying the data
* [deploy](deploy/README.md) : contains a system allowing to deploy the web application to a VPS

