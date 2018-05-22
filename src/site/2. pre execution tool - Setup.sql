-- **************************
 -- interface_metrics LOAD
-- **************************
delete from interface_metrics;
insert into interface_metrics(
project_type
,project_id
,project_name
,entity_type
,entity_id
,modifiers
,fqn
,params
,return_type
,relation_type
)
select p.project_type, p.project_id, p.name, e1.entity_type, e1.entity_id, e1.modifiers, e1.fqn, e1.params, e2.fqn 'return_type', r.relation_type
from
entities e1
,entities e2
,relations r
, projects p
where e1.entity_id = r.lhs_eid
and r.rhs_eid = e2.entity_id
and r.relation_type = 'RETURNS'
and e1.project_id = p.project_id
and e1.entity_type = 'METHOD'
 -- and p.project_type = 'CRAWLED'
and e1.modifiers like '%PUBLIC%'
and e1.modifiers not like '%ABSTRACT%'
and e1.modifiers like '%STATIC%'
and e1.params <> '()'
and e2.fqn <> 'void'
 

-- **************************
 -- Records of interface_metrics_types
-- **************************
delete from interface_metrics_types;
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'boolean');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'byte');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'char');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'double');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'float');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'int');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Boolean');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Character');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Double');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Float');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Integer');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Long');
INSERT INTO interface_metrics_types VALUES ('2', 'Java API - Primitive Wrapper', 'java.lang.Short');
INSERT INTO interface_metrics_types VALUES ('3', 'Java API - String', 'java.lang.String');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.ArrayList');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Arrays');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Collection');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Collections');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.HashMap');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.HashSet');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Hashtable');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.LinkedHashMap');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.LinkedHashSet');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.LinkedList');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.List');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Map');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.PriorityQueue');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Queue');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Set');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.SortedMap');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.SortedSet');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.TreeMap');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.TreeSet');
INSERT INTO interface_metrics_types VALUES ('4', 'Java API - Collection', 'java.util.Vector');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'long');
INSERT INTO interface_metrics_types VALUES ('1', 'Primitive', 'short');


-- **************************
 -- interface_metrics_top
-- **************************
delete from interface_metrics_top;

insert into interface_metrics_top (class, description, type, total) 
select 'R_API_TOP3' as class, 'Java API - Other (top 3 RETURN)', return_type, count(*)
from interface_metrics m
where 'JAVA_LIBRARY' in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = m.return_type)
and return_type not in (select type from interface_metrics_types)
group by class, return_type
order by count(*) DESC
limit 3;

insert into interface_metrics_top (class, description, type, total) 
select 'R_API_NOT3' as class, 'Java API - Other (not top 3 RETURN)', '', count(*)
from interface_metrics m
where 'JAVA_LIBRARY' in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = m.return_type)
and return_type not in (select type from interface_metrics_types)
and return_type not in (select type from interface_metrics_top where class = 'R_API_TOP3');

insert into interface_metrics_top (class, description, type, total) 
select 'R_API_ALL*' as class, 'Java API - Other (RETURN)', '', count(*)
from interface_metrics m
where 'JAVA_LIBRARY' in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = m.return_type)
and return_type not in (select type from interface_metrics_types);

insert into interface_metrics_top (class, description, type, total) 
select 'R_USR_TOP3' as class, 'User defined (top 3 RETURN)', return_type, count(*)
from interface_metrics m
where 'JAVA_LIBRARY' not in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = m.return_type)
and return_type not in (select type from interface_metrics_types)
group by class, return_type
order by count(*) desc
limit 3;

insert into interface_metrics_top (class, description, type, total) 
select 'R_USR_NOT3' as class, 'User defined (not top 3 RETURN)', '', count(*)
from interface_metrics m
where 'JAVA_LIBRARY' not in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = m.return_type)
and return_type not in (select type from interface_metrics_types)
and return_type not in (select type from interface_metrics_top where class = 'R_USR_TOP3');

insert into interface_metrics_top (class, description, type, total) 
select 'R_USR_ALL*' as class, 'User defined (RETURN)', '', count(*)
from interface_metrics m
where 'JAVA_LIBRARY' not in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = m.return_type)
and return_type not in (select type from interface_metrics_types);

-- ----

insert into interface_metrics_top (class, description, type, total) 
select 'P_API_TOP3' as class, 'Java API - Other (top 3 PARAM)', p.param, count(*)
from interface_metrics m, interface_metrics_params p
where 'JAVA_LIBRARY' in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = p.param)
and m.id = p.interface_metrics_id
and p.param not in (select type from interface_metrics_types)
group by class, p.param
order by count(*) DESC
limit 3;

insert into interface_metrics_top (class, description, type, total) 
select 'P_API_NOT3' as class, 'Java API - Other (not top 3 PARAM)', '', count(*)
from interface_metrics m, interface_metrics_params p
where 'JAVA_LIBRARY' in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = p.param)
and m.id = p.interface_metrics_id
and p.param not in (select type from interface_metrics_types)
and p.param not in (select type from interface_metrics_top where class = 'P_API_TOP3');

insert into interface_metrics_top (class, description, type, total) 
select 'P_API_ALL*' as class, 'Java API - Other (PARAM)', '', count(*)
from interface_metrics m, interface_metrics_params p
where 'JAVA_LIBRARY' in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = p.param)
and m.id = p.interface_metrics_id
and p.param not in (select type from interface_metrics_types);

insert into interface_metrics_top (class, description, type, total) 
select 'P_USR_TOP3' as class, 'User defined (top 3 PARAM)', p.param, count(*)
from interface_metrics m, interface_metrics_params p
where 'JAVA_LIBRARY' not in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = p.param)
and m.id = p.interface_metrics_id
and p.param not in (select type from interface_metrics_types)
group by class, p.param
order by count(*) desc
limit 3;

insert into interface_metrics_top (class, description, type, total) 
select 'P_USR_NOT3' as class, 'User defined (not top 3 PARAM)', '', count(*)
from interface_metrics m, interface_metrics_params p
where 'JAVA_LIBRARY' not in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = p.param)
and m.id = p.interface_metrics_id
and p.param not in (select type from interface_metrics_types)
and p.param not in (select type from interface_metrics_top where class = 'P_USR_TOP3');

insert into interface_metrics_top (class, description, type, total) 
select 'P_USR_ALL*' as class, 'User defined (PARAM)', '', count(*)
from interface_metrics m, interface_metrics_params p
where 'JAVA_LIBRARY' not in (select distinct p.project_type from entities e, projects p where e.project_id = p.project_id and fqn = p.param)
and m.id = p.interface_metrics_id
and p.param not in (select type from interface_metrics_types);


-- **************************
 -- interface_metrics_test
-- **************************
delete from interface_metrics_test;
INSERT INTO `interface_metrics_test` VALUES ('1000', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '14', 'PUBLIC', 'com.sun.nio.zipfs.JarFileSystemProvider.getPath', '(java.net.URI)', 'java.nio.file.Path', 'RETURNS', '1', '1', '1', '2', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
update interface_metrics_test set id = 0 where id = 1000;
INSERT INTO `interface_metrics_test` VALUES ('1', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '327', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.getFileAttributeView', '(java.nio.file.Path,java.lang.Class<<V>>,java.nio.file.LinkOption[])', '<V>', 'RETURNS', '1', '1', '3', '4', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('2', 'CRAWLED', '4', 'charsets.jar', 'METHOD', '1790', 'PUBLIC', 'sun.io.CharToByteEUC_TW.canConvert', '(char)', 'boolean', 'RETURNS', '1', '1', '1', '2', '5', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('3', 'CRAWLED', '4', 'charsets.jar', 'METHOD', '1694', 'PUBLIC', 'sun.io.CharToByteDBCS_ASCII.convert', '(char[],int,int,byte[],int,int)', 'int', 'RETURNS', '1', '1', '6', '1', '5', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('4', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '429', 'PUBLIC,STATIC', 'com.sun.nio.zipfs.ZipUtils.javaToDosTime', '(long)', 'long', 'RETURNS', '1', '1', '1', '4', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('5', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '430', 'PUBLIC,STATIC,FINAL', 'com.sun.nio.zipfs.ZipUtils.winToJavaTime', '(long)', 'long', 'RETURNS', '1', '1', '1', '4', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('6', 'CRAWLED', '6', 'rt.jar', 'METHOD', '164360', 'PUBLIC', 'java.lang.String.split', '(java.lang.String)', 'java.lang.String[]', 'RETURNS', '1', '1', '1', '1', '1', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('7', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404775', 'PUBLIC,STATIC', 'net.sf.saxon.om.FastStringBuffer.diagnosticPrint', '(java.lang.CharSequence)', 'java.lang.String', 'RETURNS', '1', '1', '1', '2', '3', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('8', 'CRAWLED', '411', 'saxon8.jar', 'METHOD', '1405499', 'PUBLIC,STATIC', 'net.sf.saxon.om.StandardNames.getURI', '(int)', 'java.lang.String', 'RETURNS', '1', '1', '1', '2', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('9', 'CRAWLED', '6', 'rt.jar', 'METHOD', '164368', 'PUBLIC,STATIC', 'java.lang.String.format', '(java.lang.String,java.lang.Object[])', 'java.lang.String', 'RETURNS', '1', '1', '2', '1', '1', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('10', 'CRAWLED', '411', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,int)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '1', '7', '3', '12', '1', '7', '3', '12', '0', '0', '0', '1', '0', '0', '0', '2');
INSERT INTO `interface_metrics_test` VALUES ('11', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,Integer)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('12', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.acquireYearnPrize', '(net.sf.saxon.tinytree.TinyTree,int)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '0', '1', '1', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('13', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'met.sf.saxon.tinytree.WhitespaceTextImpl.becomeRetentiveTreasure', '(net.sf.saxon.tinytree.TinyTree,float)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '1');
INSERT INTO `interface_metrics_test` VALUES ('14', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,int)', 'double', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('15', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,double)', 'float', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('16', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.MhitespaceTextImpl.goRecollectiveAppreciate', '(net.sf.saxon.tinytree.TinyTree,int)', 'int', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('17', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'met.sf.saxon.tinytree.MhitespaceTmextImmpl.letTenaciousRespect', '(net.sf.saxon.tinytree.TinyTree,long)', 'Integer', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1');
INSERT INTO `interface_metrics_test` VALUES ('18', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(int,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('19', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(Integer,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('20', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.acquireYearnPrize', '(int,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '0', '1', '1', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('21', 'CRAWLED', '411', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.becomeRetentiveTreasure', '(float,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '0', '0', '6', '0', '0', '0', '6', '1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `interface_metrics_test` VALUES ('1440361', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '206', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileStore.supportsFileAttributeView', '(java.lang.Class<<?+java.nio.file.attribute.FileAttributeView>>)', 'boolean', 'RETURNS', '1', '1', '1', '4', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('22', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(int,net.sf.saxon.tinytree.TinyTree)', 'double', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '1', '0', '2', '0', '1', '0', '2', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('23', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(double,net.sf.saxon.tinytree.TinyTree)', 'float', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '1', '0', '2', '0', '1', '0', '2', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('24', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.goRecollectiveAppreciate', '(int,net.sf.saxon.tinytree.TinyTree)', 'int', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '0', '0', '2', '0', '0', '0', '2', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('25', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.letTenaciousMeasure', '(long,net.sf.saxon.tinytree.TinyTree)', 'Integer', 'RETURNS', '1', '1', '2', '3', '3', '0', '1', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1810916', 'CRAWLED', '1163', '001 tullibee', 'METHOD', '5823930', 'PUBLIC,STATIC', 'com.ib.client.AnyWrapperMsgGenerator.error', '(int,int,java.lang.String)', 'java.lang.String', 'RETURNS', '1', '1', '3', '1', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1810924', 'CRAWLED', '1163', '001 tullibee', 'METHOD', '5824106', 'PUBLIC,STATIC', 'com.ib.client.EWrapperMsgGenerator.tickString', '(int,int,java.lang.String)', 'java.lang.String', 'RETURNS', '1', '1', '3', '2', '4', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1811541', 'CRAWLED', '1174', '012 dsachat', 'METHOD', '5833479', 'PUBLIC', 'dsachat.share.hero.Hero.attack', '(java.lang.String,int)', 'java.lang.String', 'RETURNS', '1', '1', '2', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1811542', 'CRAWLED', '1174', '012 dsachat', 'METHOD', '5833480', 'PUBLIC', 'dsachat.share.hero.Hero.defense', '(java.lang.String,int)', 'java.lang.String', 'RETURNS', '1', '1', '2', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1811663', 'CRAWLED', '1175', '013 jdbacl', 'METHOD', '5834477', 'PUBLIC', 'org.databene.jdbacl.dialect.H2Dialect.setSequenceValue', '(java.lang.String,long)', 'java.lang.String', 'RETURNS', '1', '1', '2', '3', '2', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1811761', 'CRAWLED', '1175', '013 jdbacl', 'METHOD', '5835322', 'PUBLIC', 'org.databene.jdbacl.dialect.HSQLDialect.renderSequenceValue', '(java.lang.String,long)', 'java.lang.String', 'RETURNS', '1', '1', '2', '3', '2', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1812767', 'CRAWLED', '1189', '027 gangup', 'METHOD', '5848346', 'PUBLIC', 'module.IRCProxyModule.ircConnect', '(java.lang.String,java.lang.String,int)', 'module.IRCSession', 'RETURNS', '1', '1', '3', '2', '3', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1812769', 'CRAWLED', '1189', '027 gangup', 'METHOD', '5848369', 'PUBLIC', 'module.IRCSession.open', '(java.lang.String,java.lang.String,int)', 'java.net.Socket', 'RETURNS', '1', '1', '3', '1', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1813702', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861764', 'PUBLIC,STATIC', 'framework.ApplicationParameters.getAsInteger', '(java.lang.String,java.lang.Integer)', 'java.lang.Integer', 'RETURNS', '1', '1', '2', '3', '2', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1813707', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861769', 'PUBLIC,STATIC', 'framework.ApplicationParameters.getAsLong', '(java.lang.String,java.lang.Long)', 'java.lang.Long', 'RETURNS', '1', '1', '2', '3', '2', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1813753', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861985', 'PUBLIC,STATIC', 'framework.util.ConvertUtils.convertLong', '(java.lang.String,java.lang.Long)', 'java.lang.Long', 'RETURNS', '1', '1', '2', '2', '2', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1813759', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861991', 'PUBLIC,STATIC', 'framework.util.ConvertUtils.convertInteger', '(java.lang.String,java.lang.Integer)', 'java.lang.Integer', 'RETURNS', '1', '1', '2', '2', '2', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1819910', 'CRAWLED', '1235', '073 fim1', 'METHOD', '5925782', 'PUBLIC', 'osa.ora.server.bo.IBO.updatePassword', '(int,java.lang.String,java.lang.String)', 'boolean', 'RETURNS', '1', '1', '3', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1819931', 'CRAWLED', '1235', '073 fim1', 'METHOD', '5925812', 'PUBLIC', 'osa.ora.server.bd.UsersBD.updatePassword', '(int,java.lang.String,java.lang.String)', 'boolean', 'RETURNS', '1', '1', '3', '2', '2', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1440371', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '327', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.getFileAttributeView', '(java.nio.file.Path,java.lang.Class<<V>>,java.nio.file.LinkOption[])', '<V>', 'RETURNS', '1', '1', '3', '4', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1440377', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '334', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.newDirectoryStream', '(java.nio.file.Path,java.nio.file.DirectoryStream$Filter<<?-java.nio.file.Path>>)', 'java.nio.file.DirectoryStream<java.nio.file.Path>', 'RETURNS', '1', '1', '2', '3', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1440618', 'CRAWLED', '6', 'rt.jar', 'METHOD', '4418', 'PUBLIC,STATIC', 'com.sun.java.util.jar.pack.Attribute.lookup', '(java.util.Map<com.sun.java.util.jar.pack.Attribute$Layout,com.sun.java.util.jar.pack.Attribute>,int,java.lang.String)', 'com.sun.java.util.jar.pack.Attribute', 'RETURNS', '1', '1', '3', '1', '1', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1440368', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '319', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.newFileSystem', '(java.nio.file.Path,java.util.Map<java.lang.String,<?>>)', 'java.nio.file.FileSystem', 'RETURNS', '1', '1', '2', '3', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1449408', 'CRAWLED', '6', 'rt.jar', 'METHOD', '84302', 'PUBLIC,STATIC', 'com.sun.xml.internal.ws.server.EndpointFactory.createEndpoint', '(java.lang.Class<<T>>,boolean,com.sun.xml.internal.ws.api.server.Invoker,javax.xml.namespace.QName,javax.xml.namespace.QName,com.sun.xml.internal.ws.api.server.Container,com.sun.xml.internal.ws.api.WSBinding,com.sun.xml.internal.ws.api.server.SDDocumentSource,java.util.Collection<<?+com.sun.xml.internal.ws.api.server.SDDocumentSource>>,org.xml.sax.EntityResolver,boolean)', 'com.sun.xml.internal.ws.api.server.WSEndpoint<<T>>', 'RETURNS', '1', '1', '11', '2', '2', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `interface_metrics_test` VALUES ('1678643', 'CRAWLED', '738', 'hibernate3.jar', 'METHOD', '2277417', 'PUBLIC,STATIC', 'org.hibernate.cfg.AnnotationBinder.fillComponent', '(org.hibernate.cfg.PropertyHolder,org.hibernate.cfg.PropertyData,org.hibernate.cfg.PropertyData,org.hibernate.cfg.AccessType,boolean,org.hibernate.cfg.annotations.EntityBinder,boolean,boolean,boolean,org.hibernate.cfg.Mappings,java.util.Map<org.hibernate.annotations.common.reflection.XClass,org.hibernate.cfg.InheritanceState>)', 'org.hibernate.mapping.Component', 'RETURNS', '1', '1', '11', '2', '2', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');


-- ----------------------------
-- interface_metrics_pairs_test
-- ----------------------------
delete from interface_metrics_pairs_test;
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '11', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '11', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '11', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '11', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '12', 'p0_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '12', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '12', 'p0_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '12', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '13', 'p1_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '13', 'p1_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '14', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '14', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '14', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '14', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '15', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '15', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '15', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '15', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '16', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '16', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '17', 'p1_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c0_w0_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c1_w0_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '18', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '19', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '19', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '19', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '19', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '20', 'p0_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '20', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '20', 'p0_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '20', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '22', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '22', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '22', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '22', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '23', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '23', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '23', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '23', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '24', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '24', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '25', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('10', '25', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('11', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('11', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('11', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('11', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('12', '10', 'p0_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('12', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('12', '10', 'p0_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('12', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('13', '10', 'p1_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('13', '10', 'p1_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('14', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('14', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('14', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('14', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('15', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('15', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('15', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('15', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('16', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('16', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('17', '10', 'p1_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c0_w0_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c1_w0_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('18', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('19', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('19', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('19', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('19', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('20', '10', 'p0_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('20', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('20', '10', 'p0_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('20', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '11', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '11', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c0_w0_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c0_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c1_w0_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c1_w1_t0');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '13', 'p1_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '14', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '14', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '15', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '15', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '16', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '16', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '18', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '18', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '19', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('21', '19', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('22', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('22', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('22', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('22', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('22', '21', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('22', '21', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('23', '10', 'p0_c0_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('23', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('23', '10', 'p0_c1_w0_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('23', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('23', '21', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('23', '21', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('24', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('24', '10', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('24', '21', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('24', '21', 'p0_c1_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('25', '10', 'p0_c0_w1_t1');
INSERT INTO `interface_metrics_pairs_test` VALUES ('25', '10', 'p0_c1_w1_t1');


-- ----------------------------
-- interface_metrics_params_test
-- ----------------------------
delete from interface_metrics_params_test;
INSERT INTO `interface_metrics_params_test` VALUES ('3650606', '0', 'java.net.URI');
INSERT INTO `interface_metrics_params_test` VALUES ('3650607', '1', 'java.nio.file.Path');
INSERT INTO `interface_metrics_params_test` VALUES ('3650608', '1', 'java.lang.Class<<V>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650609', '1', 'java.nio.file.LinkOption[]');
INSERT INTO `interface_metrics_params_test` VALUES ('3650610', '2', 'char');
INSERT INTO `interface_metrics_params_test` VALUES ('3650611', '3', 'char[]');
INSERT INTO `interface_metrics_params_test` VALUES ('3650612', '3', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650613', '3', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650614', '3', 'byte[]');
INSERT INTO `interface_metrics_params_test` VALUES ('3650615', '3', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650616', '3', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650617', '4', 'long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650618', '5', 'long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650619', '6', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650620', '7', 'java.lang.CharSequence');
INSERT INTO `interface_metrics_params_test` VALUES ('3650621', '8', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650622', '9', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650623', '9', 'java.lang.Object[]');
INSERT INTO `interface_metrics_params_test` VALUES ('3650624', '10', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650625', '10', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650626', '11', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650627', '11', 'Integer');
INSERT INTO `interface_metrics_params_test` VALUES ('3650628', '12', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650629', '12', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650630', '13', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650631', '13', 'float');
INSERT INTO `interface_metrics_params_test` VALUES ('3650632', '14', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650633', '14', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650634', '15', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650635', '15', 'double');
INSERT INTO `interface_metrics_params_test` VALUES ('3650636', '16', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650637', '16', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650638', '17', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650639', '17', 'long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650640', '18', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650641', '18', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650642', '19', 'Integer');
INSERT INTO `interface_metrics_params_test` VALUES ('3650643', '19', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650644', '20', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650645', '20', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650646', '21', 'float');
INSERT INTO `interface_metrics_params_test` VALUES ('3650647', '21', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650648', '1440361', 'java.lang.Class<<?+java.nio.file.attribute.FileAttributeView>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650649', '22', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650650', '22', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650651', '23', 'double');
INSERT INTO `interface_metrics_params_test` VALUES ('3650652', '23', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650653', '24', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650654', '24', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650655', '25', 'long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650656', '25', 'net.sf.saxon.tinytree.TinyTree');
INSERT INTO `interface_metrics_params_test` VALUES ('3650657', '1810916', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650658', '1810916', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650659', '1810916', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650660', '1810924', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650661', '1810924', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650662', '1810924', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650663', '1811541', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650664', '1811541', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650665', '1811542', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650666', '1811542', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650667', '1811663', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650668', '1811663', 'long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650669', '1811761', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650670', '1811761', 'long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650671', '1812767', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650672', '1812767', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650673', '1812767', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650674', '1812769', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650675', '1812769', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650676', '1812769', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650677', '1813702', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650678', '1813702', 'java.lang.Integer');
INSERT INTO `interface_metrics_params_test` VALUES ('3650679', '1813707', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650680', '1813707', 'java.lang.Long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650681', '1813753', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650682', '1813753', 'java.lang.Long');
INSERT INTO `interface_metrics_params_test` VALUES ('3650683', '1813759', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650684', '1813759', 'java.lang.Integer');
INSERT INTO `interface_metrics_params_test` VALUES ('3650685', '1819910', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650686', '1819910', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650687', '1819910', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650688', '1819931', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650689', '1819931', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650690', '1819931', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650691', '1440371', 'java.nio.file.Path');
INSERT INTO `interface_metrics_params_test` VALUES ('3650692', '1440371', 'java.lang.Class<<V>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650693', '1440371', 'java.nio.file.LinkOption[]');
INSERT INTO `interface_metrics_params_test` VALUES ('3650694', '1440377', 'java.nio.file.Path');
INSERT INTO `interface_metrics_params_test` VALUES ('3650695', '1440377', 'java.nio.file.DirectoryStream$Filter<<?-java.nio.file.Path>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650696', '1440618', 'java.util.Map<com.sun.java.util.jar.pack.Attribute$Layout,com.sun.java.util.jar.pack.Attribute>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650697', '1440618', 'int');
INSERT INTO `interface_metrics_params_test` VALUES ('3650698', '1440618', 'java.lang.String');
INSERT INTO `interface_metrics_params_test` VALUES ('3650699', '1440368', 'java.nio.file.Path');
INSERT INTO `interface_metrics_params_test` VALUES ('3650700', '1440368', 'java.util.Map<java.lang.String,<?>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650701', '1449408', 'java.lang.Class<<T>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650702', '1449408', 'boolean');
INSERT INTO `interface_metrics_params_test` VALUES ('3650703', '1449408', 'com.sun.xml.internal.ws.api.server.Invoker');
INSERT INTO `interface_metrics_params_test` VALUES ('3650704', '1449408', 'javax.xml.namespace.QName');
INSERT INTO `interface_metrics_params_test` VALUES ('3650705', '1449408', 'javax.xml.namespace.QName');
INSERT INTO `interface_metrics_params_test` VALUES ('3650706', '1449408', 'com.sun.xml.internal.ws.api.server.Container');
INSERT INTO `interface_metrics_params_test` VALUES ('3650707', '1449408', 'com.sun.xml.internal.ws.api.WSBinding');
INSERT INTO `interface_metrics_params_test` VALUES ('3650708', '1449408', 'com.sun.xml.internal.ws.api.server.SDDocumentSource');
INSERT INTO `interface_metrics_params_test` VALUES ('3650709', '1449408', 'java.util.Collection<<?+com.sun.xml.internal.ws.api.server.SDDocumentSource>>');
INSERT INTO `interface_metrics_params_test` VALUES ('3650710', '1449408', 'org.xml.sax.EntityResolver');
INSERT INTO `interface_metrics_params_test` VALUES ('3650711', '1449408', 'boolean');
INSERT INTO `interface_metrics_params_test` VALUES ('3650712', '1678643', 'org.hibernate.cfg.PropertyHolder');
INSERT INTO `interface_metrics_params_test` VALUES ('3650713', '1678643', 'org.hibernate.cfg.PropertyData');
INSERT INTO `interface_metrics_params_test` VALUES ('3650714', '1678643', 'org.hibernate.cfg.PropertyData');
INSERT INTO `interface_metrics_params_test` VALUES ('3650715', '1678643', 'org.hibernate.cfg.AccessType');
INSERT INTO `interface_metrics_params_test` VALUES ('3650716', '1678643', 'boolean');
INSERT INTO `interface_metrics_params_test` VALUES ('3650717', '1678643', 'org.hibernate.cfg.annotations.EntityBinder');
INSERT INTO `interface_metrics_params_test` VALUES ('3650718', '1678643', 'boolean');
INSERT INTO `interface_metrics_params_test` VALUES ('3650719', '1678643', 'boolean');
INSERT INTO `interface_metrics_params_test` VALUES ('3650720', '1678643', 'boolean');
INSERT INTO `interface_metrics_params_test` VALUES ('3650721', '1678643', 'org.hibernate.cfg.Mappings');
INSERT INTO `interface_metrics_params_test` VALUES ('3650722', '1678643', 'java.util.Map<org.hibernate.annotations.common.reflection.XClass,org.hibernate.cfg.InheritanceState>');
