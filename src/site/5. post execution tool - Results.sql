 -- #01 Totais gerais
select entity_type, '** TOTAL', count(*), (count(*) * 100 / (select count(*) from interface_metrics)) '%' from interface_metrics
union all select entity_type, project_type, count(*) 'TOTAL', (count(*) * 100 / (select count(*) from interface_metrics)) '%' from interface_metrics GROUP BY project_type

 -- #02 Entities Filter
select * from interface_metrics_filter;

 -- #03 Return profile
select * from v_profile_return;

 -- #04 Params profile
select * from v_profile_params;

 -- #05 Totals: Primitive types, static, same package
select 'TOTAL', count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif
union all select '---', '---', '---' from dual
union all select 'Only primitive types', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif where only_primitive_types = 1
union all select 'NOT only primitivetypes', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif where only_primitive_types = 0
union all select '---', '---', '---' from dual
union all select 'is_static', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif where is_static = 1
union all select 'NOT static', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif where is_static = 0
union all select '---', '---', '---' from dual
union all select 'has_type_same_package', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif where has_type_same_package = 1
union all select 'NOT has_type_same_package', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif where has_type_same_package = 0

 -- #06 Total: Params
select total_params, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif GROUP BY total_params order by count(*) DESC

 -- #07 Total: Words Method
select total_words_method, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif GROUP BY total_words_method order by count(*) DESC

 -- #08 Total: Words Class
select total_words_class, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%' from v_interface_metrics_dif GROUP BY total_words_class order by count(*) DESC

 -- #09 Search match
select 'TOTAL' Searchs, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ) '%', '' avg, '' max, '' min from v_interface_metrics_dif
union all select '--- Interface types: ', '---', '---', '---', '---', '---' from dual
union all select 'A (pacote diferente e mesmos: retorno, nome, parâmetros)' , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w0_t0), max(p1_c1_w0_t0), min(p1_c1_w0_t0) from v_interface_metrics_dif where p1_c1_w0_t0 > 0
union all select 'B (pacote diferente e mesmos: classe, retorno, nome, parâmetros)' , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w0_t0), max(p1_c0_w0_t0), min(p1_c0_w0_t0) from v_interface_metrics_dif where p1_c0_w0_t0 > 0
union all select 'C (mesmos: pacote, classe, retorno, nome, parâmetros)' , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p0_c0_w0_t0), max(p0_c0_w0_t0), min(p0_c0_w0_t0) from v_interface_metrics_dif where p0_c0_w0_t0 > 0
union all select '--- Expansions types: ', '---', '---', '---', '---', '---' from dual
union all select 'AWT - A com Expansão (nome e tipos)', count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w1_t1), max(p1_c1_w1_t1), min(p1_c1_w1_t1) from v_interface_metrics_dif where p1_c1_w1_t1 > 0
union all select 'AW  - A com Expansão (nome)'        , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w1_t0), max(p1_c1_w1_t0), min(p1_c1_w1_t0) from v_interface_metrics_dif where p1_c1_w1_t0 > 0
union all select 'AT  - A com Expansão (tipos)'       , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w0_t1), max(p1_c1_w0_t1), min(p1_c1_w0_t1) from v_interface_metrics_dif where p1_c1_w0_t1 > 0
union all select 'BWT - B com Expansão (nome e tipos)', count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w1_t1), max(p1_c0_w1_t1), min(p1_c0_w1_t1) from v_interface_metrics_dif where p1_c0_w1_t1 > 0
union all select 'BW  - B com Expansão (nome)'        , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w1_t0), max(p1_c0_w1_t0), min(p1_c0_w1_t0) from v_interface_metrics_dif where p1_c0_w1_t0 > 0
union all select 'BT  - B com Expansão (tipos)'       , count(*) , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w0_t1), max(p1_c0_w0_t1), min(p1_c0_w0_t1) from v_interface_metrics_dif where p1_c0_w0_t1 > 0
union all select '--- Ganhos: ', '---', '---', '---', '---', '---' from dual
union all select 'Interface não encontrada',        count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), '', '', '' from v_interface_metrics_dif where p0_c0_w0_t0 = 0
union all select 'Passou a ser encontrada com AWT', count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w1_t1), max(p1_c1_w1_t1), min(p1_c1_w1_t1) from v_interface_metrics_dif where p0_c0_w0_t0 = 0 and dif_p1_c1_w1_t1 > 0
union all select 'Passou a ser encontrada com AW' , count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w1_t0), max(p1_c1_w1_t0), min(p1_c1_w1_t0) from v_interface_metrics_dif where p0_c0_w0_t0 = 0 and dif_p1_c1_w1_t0 > 0
union all select 'Passou a ser encontrada com AT' , count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c1_w0_t1), max(p1_c1_w0_t1), min(p1_c1_w0_t1) from v_interface_metrics_dif where p0_c0_w0_t0 = 0 and dif_p1_c1_w0_t1 > 0
union all select 'Passou a ser encontrada com BWT', count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w1_t1), max(p1_c0_w1_t1), min(p1_c0_w1_t1) from v_interface_metrics_dif where p0_c0_w0_t0 = 0 and dif_p1_c0_w1_t1 > 0
union all select 'Passou a ser encontrada com BW' , count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w1_t0), max(p1_c0_w1_t0), min(p1_c0_w1_t0) from v_interface_metrics_dif where p0_c0_w0_t0 = 0 and dif_p1_c0_w1_t0 > 0
union all select 'Passou a ser encontrada com BT' , count(*), (count(*) * 100 / (select count(*) total from v_interface_metrics_dif) ), avg(p1_c0_w0_t1), max(p1_c0_w0_t1), min(p1_c0_w0_t1) from v_interface_metrics_dif where p0_c0_w0_t0 = 0 and dif_p1_c0_w0_t1 > 0

 -- #10 AWT e BWT / Primitive types, static, same package
select 'AWT - A com Expansão (nome e tipos)' Searchs, count(*) total , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', '' avg, '' max, '' min from v_interface_metrics_dif where p1_c1_w1_t1 > 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select 'Only primitive types'    , count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0 and only_primitive_types = 1
union all select 'NOT only primitive types', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0 and only_primitive_types = 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select 'is_static' , count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0 and is_static = 1
union all select 'NOT static', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0 and is_static = 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select 'has_type_same_package'    , count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0 and has_type_same_package = 1
union all select 'NOT has_type_same_package', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0 and has_type_same_package = 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select '---', '---', '---', '---', '---', '---' from dual
union all 
select 'BWT - B com Expansão (nome e tipos)' Searchs, count(*) total , (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', '' avg, '' max, '' min from v_interface_metrics_dif where p1_c0_w1_t1 > 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select 'Only primitive types'    , count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0 and only_primitive_types = 1
union all select 'NOT only primitive types', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0 and only_primitive_types = 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select 'is_static' , count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0 and is_static = 1
union all select 'NOT static', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0 and is_static = 0
union all select '---', '---', '---', '---', '---', '---' from dual
union all select 'has_type_same_package'    , count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0 and has_type_same_package = 1
union all select 'NOT has_type_same_package', count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0 and has_type_same_package = 0

 -- #11 AWT / Params
select total_params, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0
GROUP BY total_params order by count(*) desc

 -- #12 BWT / Params
select total_params, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0
GROUP BY total_params order by count(*) desc

 -- #13 AWT / Words Method
select total_words_method, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0
GROUP BY total_words_method order by count(*) desc

 -- #14 BWT / Words Method
select total_words_method, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0
GROUP BY total_words_method order by count(*) desc

 -- #15 AWT / Words Class
select total_words_class, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c1_w1_t1 > 0) ) '%', avg(p1_c1_w1_t1) avg, max(p1_c1_w1_t1) max, min(p1_c1_w1_t1) min from v_interface_metrics_dif where p1_c1_w1_t1 > 0
GROUP BY total_words_class order by count(*) desc

 -- #16 BWT / Words Class
select total_words_class, count(*) total, (count(*) * 100 / (select count(*) total from v_interface_metrics_dif where p1_c0_w1_t1 > 0) ) '%', avg(p1_c0_w1_t1) avg, max(p1_c0_w1_t1) max, min(p1_c0_w1_t1) min from v_interface_metrics_dif where p1_c0_w1_t1 > 0
GROUP BY total_words_class order by count(*) desc

 -- #17 Métodos similares por projeto - Total geral
select count(*) 'Projects',
       'AVG' metric, avg(search_interface) as search_interface,
       avg(similar_AWT) as similar_AWT,
       ((avg(similar_AWT) / avg(search_interface)) * 100) as '% similar_AWT',
       avg(similar_BWT) as similar_BWT,
       ((avg(similar_BWT) / avg(search_interface)) * 100) as '% similar_BWT',
       avg(same_interface) as same_interface,
       ((avg(same_interface) / avg(search_interface)) * 100) as '% same_interface',
       avg(same_class_interface) as same_class_interface,
       ((avg(same_class_interface) / avg(search_interface)) * 100) as '% same_class_interface'
from v_profile_project as p where search_interface > 0
union all
select count(*) 'Projects',
       'SUM' metric, sum(search_interface) as search_interface,
       sum(similar_AWT) as similar_AWT,
       ((sum(similar_AWT) / sum(search_interface)) * 100) as '% similar_AWT',
       sum(similar_BWT) as similar_BWT,
       ((sum(similar_BWT) / sum(search_interface)) * 100) as '% similar_BWT',
       sum(same_interface) as same_interface,
       ((sum(same_interface) / sum(search_interface)) * 100) as '% same_interface',
       sum(same_class_interface) as same_class_interface,
       ((sum(same_class_interface) / sum(search_interface)) * 100) as '% same_class_interface'
from v_profile_project as p where search_interface > 0

 -- #18 Métodos similares por projeto
select project_id, project_name, search_interface,
       similar_AWT, ((similar_AWT / search_interface) * 100) as '% similar_AWT',
       similar_BWT, ((similar_BWT / search_interface) * 100) as '% similar_BWT',
       same_interface, ((same_interface / search_interface) * 100) as '% same_interface',
       same_class_interface, ((same_class_interface / search_interface) * 100) as '% same_class_interface'
from v_profile_project as p  where search_interface > 0
order by 5 desc, 1

 -- #19 Pares de redundância de interface por projeto
select i.project_id, i.name,
(
select   count(*)
from     interface_metrics_pairs_inner p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w0_t0'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'intra-a-total',
(
select   count(*)
from     interface_metrics_pairs_inner p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w0_t0'
and      b.project_type = 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'intra-a-crawled_crawled',
(
select   count(*)
from     interface_metrics_pairs_inner p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w0_t0'
and      b.project_type <> 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'intra-a-crawled_library',
(
select   count(*)
from     interface_metrics_pairs_inner p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w1_t1'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'intra-awt-total',
(
select   count(*)
from     interface_metrics_pairs_inner p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w1_t1'
and      b.project_type = 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'intra-awt-crawled_crawled',
(
select   count(*)
from     interface_metrics_pairs_inner p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w1_t1'
and      b.project_type <> 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'intra-awt-crawled_library',
(
select   count(*)
from     interface_metrics_pairs p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w0_t0'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'inter-a-total',
(
select   count(*)
from     interface_metrics_pairs p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w0_t0'
and      b.project_type = 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'inter-a-crawled_crawled',
(
select   count(*)
from     interface_metrics_pairs p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w0_t0'
and      b.project_type <> 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'inter-a-crawled_library',
(
select   count(*)
from     interface_metrics_pairs p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w1_t1'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'inter-awt-total',
(
select   count(*)
from     interface_metrics_pairs p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w1_t1'
and      b.project_type = 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'inter-awt-crawled_crawled',
(
select   count(*)
from     interface_metrics_pairs p,
         interface_metrics a,
         interface_metrics b
where    p.interface_metrics_a = a.id
and      p.interface_metrics_b = b.id
and      p.search_type = 'p1_c1_w1_t1'
and      b.project_type <> 'CRAWLED'
and      a.project_id = i.project_id
group by a.project_id, a.project_name, a.project_type
) as 'inter-awt-crawled_library'
from projects i
where project_type = 'CRAWLED'
order by i.name

