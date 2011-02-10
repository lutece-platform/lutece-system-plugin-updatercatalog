--
-- Table structure for table updatercatalog_catalog
--
DROP TABLE IF EXISTS updatercatalog_catalog;
CREATE TABLE updatercatalog_catalog (
	id_catalog int NOT NULL,
	catalog_locale char(2) NOT NULL,
	catalog_name varchar(50) NOT NULL,
	catalog_description varchar(255) default NULL,
	output_filename varchar(255) NOT NULL,
	url_repository varchar(255) NOT NULL,
	PRIMARY KEY (id_catalog)
);



--
-- Table structure for table updatercatalog_plugin
--
DROP TABLE IF EXISTS updatercatalog_plugin;
CREATE TABLE updatercatalog_plugin (
	plugin_name varchar(50) NOT NULL,
	plugin_locale char(2) NOT NULL,
	plugin_description varchar(255) default NULL,
	plugin_author varchar(50) default NULL,
	url_homepage varchar(255) default NULL,
	PRIMARY KEY (plugin_name,plugin_locale)
);

--
-- Table structure for table updatercatalog_plugin_release
--
DROP TABLE IF EXISTS updatercatalog_plugin_release;
CREATE TABLE updatercatalog_plugin_release (
	id_release int NOT NULL,
	plugin_name varchar(50) NOT NULL,
	plugin_version varchar(20) default NULL,
	url_download varchar(255) default NULL,
	core_version_min varchar(20) default NULL,
	core_version_max varchar(20) default NULL,
	PRIMARY KEY (id_release)
);

--
-- Table structure for table updatercatalog_plugin_upgrade
--
DROP TABLE IF EXISTS updatercatalog_plugin_upgrade;
CREATE TABLE updatercatalog_plugin_upgrade (
	id_release int NOT NULL,
	version_from varchar(50) default NULL,
	critical_upgrade int default NULL,
	url_download varchar(255) default NULL,
	PRIMARY KEY (id_release,version_from)
);

--
-- Table structure for table updatercatalog_catalog_plugin_release
--
DROP TABLE IF EXISTS updatercatalog_catalog_plugin_release;
CREATE TABLE updatercatalog_catalog_plugin_release (
	id_catalog int NOT NULL,
	id_release int NOT NULL,
	PRIMARY KEY (id_catalog,id_release)
);
