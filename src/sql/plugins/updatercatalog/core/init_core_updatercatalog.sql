--
-- Dumping data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'UPDATER_CATALOG_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('UPDATER_CATALOG_MANAGEMENT','updatercatalog.adminFeature.catalog_management.name',0,'jsp/admin/plugins/updatercatalog/ManageCatalogs.jsp','updatercatalog.adminFeature.catalog_management.description',0,'updatercatalog',NULL,NULL,NULL,4);


--
-- Dumping data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'UPDATER_CATALOG_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('UPDATER_CATALOG_MANAGEMENT',1);
