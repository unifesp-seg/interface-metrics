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
and e1.entity_type = 'METHOD';
 

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


-- -------------------------------------------------------------------------------------------------------------------------------- 
-- ---------------------------- TEST ---------------------------------------------------------------------------------------------- 
-- -------------------------------------------------------------------------------------------------------------------------------- 

-- **************************
 -- interface_metrics_test
-- **************************
delete from interface_metrics_params_test;
delete from interface_metrics_pairs_test;
delete from interface_metrics_test;
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1000', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '14', 'PUBLIC', 'com.sun.nio.zipfs.JarFileSystemProvider.getPath', '(java.net.URI)', 'java.nio.file.Path', 'RETURNS');
update interface_metrics_test set id = 0 where id = 1000;
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '327', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.getFileAttributeView', '(java.nio.file.Path,java.lang.Class<<V>>,java.nio.file.LinkOption[])', '<V>', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('2', 'CRAWLED', '4', 'charsets.jar', 'METHOD', '1790', 'PUBLIC', 'sun.io.CharToByteEUC_TW.canConvert', '(char)', 'boolean', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('3', 'CRAWLED', '4', 'charsets.jar', 'METHOD', '1694', 'PUBLIC', 'sun.io.CharToByteDBCS_ASCII.convert', '(char[],int,int,byte[],int,int)', 'int', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('4', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '429', 'PUBLIC,STATIC', 'com.sun.nio.zipfs.ZipUtils.javaToDosTime', '(long)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('5', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '430', 'PUBLIC,STATIC,FINAL', 'com.sun.nio.zipfs.ZipUtils.winToJavaTime', '(long)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('6', 'CRAWLED', '6', 'rt.jar', 'METHOD', '164360', 'PUBLIC', 'java.lang.String.split', '(java.lang.String)', 'java.lang.String[]', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('7', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404775', 'PUBLIC,STATIC', 'net.sf.saxon.om.FastStringBuffer.diagnosticPrint', '(java.lang.CharSequence)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('8', 'CRAWLED', '411', 'saxon8.jar', 'METHOD', '1405499', 'PUBLIC,STATIC', 'net.sf.saxon.om.StandardNames.getURI', '(int)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('9', 'CRAWLED', '6', 'rt.jar', 'METHOD', '164368', 'PUBLIC,STATIC', 'java.lang.String.format', '(java.lang.String,java.lang.Object[])', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('10', 'CRAWLED', '411', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,int)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('11', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,Integer)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('12', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.acquireYearnPrize', '(net.sf.saxon.tinytree.TinyTree,int)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('13', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'met.sf.saxon.tinytree.WhitespaceTextImpl.becomeRetentiveTreasure', '(net.sf.saxon.tinytree.TinyTree,float)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('14', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,int)', 'double', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('15', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(net.sf.saxon.tinytree.TinyTree,double)', 'float', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('16', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.MhitespaceTextImpl.goRecollectiveAppreciate', '(net.sf.saxon.tinytree.TinyTree,int)', 'int', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('17', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'met.sf.saxon.tinytree.MhitespaceTmextImmpl.letTenaciousRespect', '(net.sf.saxon.tinytree.TinyTree,long)', 'Integer', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('18', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(int,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('19', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(Integer,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('20', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.acquireYearnPrize', '(int,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('21', 'CRAWLED', '411', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.becomeRetentiveTreasure', '(float,net.sf.saxon.tinytree.TinyTree)', 'long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1440361', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '206', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileStore.supportsFileAttributeView', '(java.lang.Class<<?+java.nio.file.attribute.FileAttributeView>>)', 'boolean', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('22', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(int,net.sf.saxon.tinytree.TinyTree)', 'double', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('23', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue', '(double,net.sf.saxon.tinytree.TinyTree)', 'float', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('24', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.goRecollectiveAppreciate', '(int,net.sf.saxon.tinytree.TinyTree)', 'int', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('25', 'CRAWLED', '410', 'saxon8.jar', 'METHOD', '1404528', 'PUBLIC,STATIC', 'net.sf.saxon.tinytree.WhitespaceTextImpl.letTenaciousMeasure', '(long,net.sf.saxon.tinytree.TinyTree)', 'Integer', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1810916', 'CRAWLED', '1163', '001 tullibee', 'METHOD', '5823930', 'PUBLIC,STATIC', 'com.ib.client.AnyWrapperMsgGenerator.error', '(int,int,java.lang.String)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1810924', 'CRAWLED', '1163', '001 tullibee', 'METHOD', '5824106', 'PUBLIC,STATIC', 'com.ib.client.EWrapperMsgGenerator.tickString', '(int,int,java.lang.String)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1811541', 'CRAWLED', '1174', '012 dsachat', 'METHOD', '5833479', 'PUBLIC', 'dsachat.share.hero.Hero.attack', '(java.lang.String,int)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1811542', 'CRAWLED', '1174', '012 dsachat', 'METHOD', '5833480', 'PUBLIC', 'dsachat.share.hero.Hero.defense', '(java.lang.String,int)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1811663', 'CRAWLED', '1175', '013 jdbacl', 'METHOD', '5834477', 'PUBLIC', 'org.databene.jdbacl.dialect.H2Dialect.setSequenceValue', '(java.lang.String,long)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1811761', 'CRAWLED', '1175', '013 jdbacl', 'METHOD', '5835322', 'PUBLIC', 'org.databene.jdbacl.dialect.HSQLDialect.renderSequenceValue', '(java.lang.String,long)', 'java.lang.String', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1812767', 'CRAWLED', '1189', '027 gangup', 'METHOD', '5848346', 'PUBLIC', 'module.IRCProxyModule.ircConnect', '(java.lang.String,java.lang.String,int)', 'module.IRCSession', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1812769', 'CRAWLED', '1189', '027 gangup', 'METHOD', '5848369', 'PUBLIC', 'module.IRCSession.open', '(java.lang.String,java.lang.String,int)', 'java.net.Socket', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1813702', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861764', 'PUBLIC,STATIC', 'framework.ApplicationParameters.getAsInteger', '(java.lang.String,java.lang.Integer)', 'java.lang.Integer', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1813707', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861769', 'PUBLIC,STATIC', 'framework.ApplicationParameters.getAsLong', '(java.lang.String,java.lang.Long)', 'java.lang.Long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1813753', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861985', 'PUBLIC,STATIC', 'framework.util.ConvertUtils.convertLong', '(java.lang.String,java.lang.Long)', 'java.lang.Long', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1813759', 'CRAWLED', '1200', '038 javabullboard', 'METHOD', '5861991', 'PUBLIC,STATIC', 'framework.util.ConvertUtils.convertInteger', '(java.lang.String,java.lang.Integer)', 'java.lang.Integer', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1819910', 'CRAWLED', '1235', '073 fim1', 'METHOD', '5925782', 'PUBLIC', 'osa.ora.server.bo.IBO.updatePassword', '(int,java.lang.String,java.lang.String)', 'boolean', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1819931', 'CRAWLED', '1235', '073 fim1', 'METHOD', '5925812', 'PUBLIC', 'osa.ora.server.bd.UsersBD.updatePassword', '(int,java.lang.String,java.lang.String)', 'boolean', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1440371', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '327', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.getFileAttributeView', '(java.nio.file.Path,java.lang.Class<<V>>,java.nio.file.LinkOption[])', '<V>', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1440377', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '334', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.newDirectoryStream', '(java.nio.file.Path,java.nio.file.DirectoryStream$Filter<<?-java.nio.file.Path>>)', 'java.nio.file.DirectoryStream<java.nio.file.Path>', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1440618', 'CRAWLED', '6', 'rt.jar', 'METHOD', '4418', 'PUBLIC,STATIC', 'com.sun.java.util.jar.pack.Attribute.lookup', '(java.util.Map<com.sun.java.util.jar.pack.Attribute$Layout,com.sun.java.util.jar.pack.Attribute>,int,java.lang.String)', 'com.sun.java.util.jar.pack.Attribute', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1440368', 'CRAWLED', '3', 'zipfs.jar', 'METHOD', '319', 'PUBLIC', 'com.sun.nio.zipfs.ZipFileSystemProvider.newFileSystem', '(java.nio.file.Path,java.util.Map<java.lang.String,<?>>)', 'java.nio.file.FileSystem', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1449408', 'CRAWLED', '6', 'rt.jar', 'METHOD', '84302', 'PUBLIC,STATIC', 'com.sun.xml.internal.ws.server.EndpointFactory.createEndpoint', '(java.lang.Class<<T>>,boolean,com.sun.xml.internal.ws.api.server.Invoker,javax.xml.namespace.QName,javax.xml.namespace.QName,com.sun.xml.internal.ws.api.server.Container,com.sun.xml.internal.ws.api.WSBinding,com.sun.xml.internal.ws.api.server.SDDocumentSource,java.util.Collection<<?+com.sun.xml.internal.ws.api.server.SDDocumentSource>>,org.xml.sax.EntityResolver,boolean)', 'com.sun.xml.internal.ws.api.server.WSEndpoint<<T>>', 'RETURNS');
INSERT INTO `interface_metrics_test` (id, project_type, project_id ,project_name ,entity_type ,entity_id ,modifiers ,fqn ,params ,return_type ,relation_type) VALUES ('1678643', 'CRAWLED', '738', 'hibernate3.jar', 'METHOD', '2277417', 'PUBLIC,STATIC', 'org.hibernate.cfg.AnnotationBinder.fillComponent', '(org.hibernate.cfg.PropertyHolder,org.hibernate.cfg.PropertyData,org.hibernate.cfg.PropertyData,org.hibernate.cfg.AccessType,boolean,org.hibernate.cfg.annotations.EntityBinder,boolean,boolean,boolean,org.hibernate.cfg.Mappings,java.util.Map<org.hibernate.annotations.common.reflection.XClass,org.hibernate.cfg.InheritanceState>)', 'org.hibernate.mapping.Component', 'RETURNS');


-- ----------------------------------------------------------------------------
-- Selects para acompanhar a execução de cada etapa do App: interface-metrics
-- ----------------------------------------------------------------------------

-- 1. Process only method info
select count(*) from interface_metrics
where total_params is NULL;

-- 2. ExecutionType.PARAMS
select count(*) from interface_metrics
where (processed_params <> 1 or processed_params is null);

-- 3. ExecutionType.INTERFACE_METRICS
select count(*) from interface_metrics where project_type = 'CRAWLED'
and (processed <> 1 or processed is null);

