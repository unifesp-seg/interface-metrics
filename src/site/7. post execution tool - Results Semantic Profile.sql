 -- #26 Totals: Primitive types, static, same package (métodos redundantes match 4)
select 'TOTAL', count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
union all select '---', '---', '---' from dual
union all
select 'Only primitive types', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where only_primitive_types = 1 and id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
union all
select 'NOT only primitivetypes', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where only_primitive_types = 0 and id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
union all
select '---', '---', '---' from dual
union all
select 'is_static', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where is_static = 1 and id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
union all
select 'NOT static', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where is_static = 0 and id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
union all
select '---', '---', '---' from dual
union all
select 'has_type_same_package', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where has_type_same_package = 1 and id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
union all
select 'NOT has_type_same_package', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where has_type_same_package = 0 and id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')

 -- #27 Total: Params (métodos redundantes match 4)
select total_params, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
GROUP BY total_params order by count(*) DESC

 -- #28 Total: Words Method (métodos redundantes match 4)
select total_words_method, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
GROUP BY total_words_method order by count(*) DESC

 -- #29 Total: Words Class (métodos redundantes match 4)
select total_words_class, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')) ) '%'
from   v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
GROUP BY total_words_class order by count(*) DESC

-- #30 Return profile (métodos redundantes match 4)
select return_type, count(*) 'total',(count(*) * 100 /
       (select count(*) total from v_interface_metrics_dif where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1'))) as '%'
from interface_metrics
where id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
GROUP BY return_type

-- #31 Params profile (métodos redundantes match 4)
select param, count(*) 'total',(count(*) * 100 / 
       (select count(*) from interface_metrics_params p, interface_metrics i
        where p.interface_metrics_id = i.id
        and i.id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1'))) as '%'
from interface_metrics_params p, interface_metrics i
where p.interface_metrics_id = i.id
and i.id in (select distinct a_id from v_interface_metrics_pairs where result = 4 and search_type = 'p1_c1_w1_t1')
GROUP BY param
