-- **************************
 -- v_interface_metrics_dif
-- **************************
DROP view IF EXISTS v_interface_metrics_dif;
CREATE view v_interface_metrics_dif as
select
 p0_c0_w0_t0 - p0_c0_w0_t0 as dif_p0_c0_w0_t0
,p0_c0_w0_t1 - p0_c0_w0_t0 as dif_p0_c0_w0_t1
,p0_c0_w1_t0 - p0_c0_w0_t0 as dif_p0_c0_w1_t0
,p0_c0_w1_t1 - p0_c0_w0_t0 as dif_p0_c0_w1_t1
,p0_c1_w0_t0 - p0_c0_w0_t0 as dif_p0_c1_w0_t0
,p0_c1_w0_t1 - p0_c0_w0_t0 as dif_p0_c1_w0_t1
,p0_c1_w1_t0 - p0_c0_w0_t0 as dif_p0_c1_w1_t0
,p0_c1_w1_t1 - p0_c0_w0_t0 as dif_p0_c1_w1_t1
,p1_c0_w0_t0 - p0_c0_w0_t0 as dif_p1_c0_w0_t0
,p1_c0_w0_t1 - p0_c0_w0_t0 as dif_p1_c0_w0_t1
,p1_c0_w1_t0 - p0_c0_w0_t0 as dif_p1_c0_w1_t0
,p1_c0_w1_t1 - p0_c0_w0_t0 as dif_p1_c0_w1_t1
,p1_c1_w0_t0 - p0_c0_w0_t0 as dif_p1_c1_w0_t0
,p1_c1_w0_t1 - p0_c0_w0_t0 as dif_p1_c1_w0_t1
,p1_c1_w1_t0 - p0_c0_w0_t0 as dif_p1_c1_w1_t0
,p1_c1_w1_t1 - p0_c0_w0_t0 as dif_p1_c1_w1_t1
,m.*
from interface_metrics as m
where project_type = 'CRAWLED';


-- **************************
 -- v_profile_return
-- **************************
DROP view IF EXISTS v_profile_return;
CREATE view v_profile_return as
select 'Primitive' as 'Class', return_type, count(*) as 'sub total', (count(*) * 100 / (select count(*) from interface_metrics)) as '%', '' as total, '' as 'total%'
from interface_metrics
where return_type in (select type from interface_metrics_types where class = 1)
GROUP BY return_type
union ALL
select 'Primitive', '', '', '', count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics
where return_type in (select type from interface_metrics_types where class = 1)

union ALL
select 'Java API - Primitive Wrapper', return_type, count(*), (count(*) * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics
where return_type in (select type from interface_metrics_types where class = 2)
GROUP BY return_type
union ALL
select 'Java API - Primitive Wrapper', '', '', '', count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics
where return_type in (select type from interface_metrics_types where class = 2)

union ALL
select 'Java API - String', return_type, count(*), (count(*) * 100 / (select count(*) from interface_metrics)), count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics
where return_type = (select type from interface_metrics_types where class = 3)

union ALL
-- http://www.devmedia.com.br/visao-geral-da-interface-collection-em-java/25822
select 'Java API - Collection', return_type, count(*), (count(*) * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics
where return_type in (select type from interface_metrics_types where class = 4)
GROUP BY return_type
union ALL
select 'Java API - Collection', '', '', '', count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics
where return_type in (select type from interface_metrics_types where class = 4)

union ALL
select description, type, total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'R_API_TOP3'
union ALL
select description, '', total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'R_API_NOT3'
union ALL
select description, '', '', '', total, (total * 100 / (select count(*) from interface_metrics))
from interface_metrics_top
where class = 'R_API_ALL*'

union ALL
select description, type, total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'R_USR_TOP3'
union ALL
select description, '', total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'R_USR_NOT3'
union ALL
select description, '', '', '', total, (total * 100 / (select count(*) from interface_metrics))
from interface_metrics_top
where class = 'R_USR_ALL*';


-- **************************
 -- v_profile_params
-- **************************
DROP view IF EXISTS v_profile_params;
CREATE view v_profile_params as
select 'Primitive' as 'Class', param, count(*) as 'sub total', (count(*) * 100 / (select count(*) from interface_metrics)) as '%', '' as total, '' as 'total%'
from interface_metrics_params
where param in (select type from interface_metrics_types where class = 1)
GROUP BY param
union ALL
select 'Primitive', '', '', '', count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics_params
where param in (select type from interface_metrics_types where class = 1)

union ALL
select 'Java API - Primitive Wrapper', param, count(*), (count(*) * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_params
where param in (select type from interface_metrics_types where class = 2)
GROUP BY param
union ALL
select 'Java API - Primitive Wrapper', '', '', '', count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics_params
where param in (select type from interface_metrics_types where class = 2)

union ALL
select 'Java API - String', param, count(*), (count(*) * 100 / (select count(*) from interface_metrics)), count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics_params
where param = (select type from interface_metrics_types where class = 3)

union ALL
-- http://www.devmedia.com.br/visao-geral-da-interface-collection-em-java/25822
select 'Java API - Collection', param, count(*), (count(*) * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_params
where param in (select type from interface_metrics_types where class = 4)
GROUP BY param
union ALL
select 'Java API - Collection', '', '', '', count(*), (count(*) * 100 / (select count(*) from interface_metrics))
from interface_metrics_params
where param in (select type from interface_metrics_types where class = 4)

union ALL
select description, type, total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'P_API_TOP3'
union ALL
select description, '', total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'P_API_NOT3'
union ALL
select description, '', '', '', total, (total * 100 / (select count(*) from interface_metrics))
from interface_metrics_top
where class = 'P_API_ALL*'

union ALL
select description, type, total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'P_USR_TOP3'
union ALL
select description, '', total, (total * 100 / (select count(*) from interface_metrics)), '', ''
from interface_metrics_top
where class = 'P_USR_NOT3'
union ALL
select description, '', '', '', total, (total * 100 / (select count(*) from interface_metrics))
from interface_metrics_top
where class = 'P_USR_ALL*';


-- **************************
 -- v_profile_project
-- **************************
DROP view IF EXISTS v_profile_project;
CREATE view v_profile_project as
select project_id, project_name, count(*) as 'search_interface'
,(
SELECT count(*)
from v_interface_metrics_dif i
where i.project_id = p.project_id
and p1_c1_w1_t1 > 0
) as 'similar_AWT'
,(
SELECT count(*)
from v_interface_metrics_dif i
where i.project_id = p.project_id
and p1_c0_w1_t1 > 0
) as 'similar_BWT'
,(
SELECT count(*)
from v_interface_metrics_dif i
where i.project_id = p.project_id
and p1_c1_w0_t0 > 0
) as 'same_interface'
,(
SELECT count(*)
from v_interface_metrics_dif i
where i.project_id = p.project_id
and p1_c0_w0_t0 > 0
) as 'same_class_interface'
from interface_metrics p
where project_type = 'CRAWLED'
group by project_id, project_name;


-- **************************
 -- v_interface_metrics_pairs
-- **************************
DROP view IF EXISTS v_interface_metrics_pairs;
CREATE view v_interface_metrics_pairs as
select p.search_type,
       
       ((CASE WHEN a.result1 = b.result1 THEN 1 else 0 END) +
        (CASE WHEN a.result2 = b.result2 THEN 1 else 0 END) +
        (CASE WHEN a.result3 = b.result3 THEN 1 else 0 END) +
        (CASE WHEN a.result4 = b.result4 THEN 1 else 0 END)) as 'result_sql',

       p.result,

       a.project_type a_project_type,
       a.project_id a_project_id,
       a.project_name a_project_name,
       a.entity_id a_entity_id,
       a.id a_id,

       concat(a.modifiers,' ',a.return_type, ' ', a.fqn, a.params) a_fqn,
       concat(b.modifiers,' ',b.return_type, ' ', b.fqn, b.params) b_fqn,

       b.id b_id,
       b.entity_id b_entity_id,
       b.project_name b_project_name,
       b.project_id b_project_id,
       b.project_type b_project_type,

       a.result1 a_result1,
       a.result2 a_result2,
       a.result3 a_result3,
       a.result4 a_result4,
       a.exec1 a_exec1,
       a.exec2 a_exec2,
       a.exec3 a_exec3,
       a.exec4 a_exec4,
       a.error a_error,

       b.result1 b_result1,
       b.result2 b_result2,
       b.result3 b_result3,
       b.result4 b_result4,
       b.exec1 b_exec1,
       b.exec2 b_exec2,
       b.exec3 b_exec3,
       b.exec4 b_exec4,
       b.error b_error

from   interface_metrics_pairs p,
       interface_metrics a,
       interface_metrics b
where  a.id = p.interface_metrics_a
and    b.id = p.interface_metrics_b;

