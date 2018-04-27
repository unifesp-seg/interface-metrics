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
 -- interface_metrics_top TOP
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
