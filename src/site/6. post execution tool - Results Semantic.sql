 -- Query para obter os ids a serem processados para a redundância semântica
ct distinct t.id 
 from
 	(
	select id from interface_metrics_pairs p, interface_metrics i
	where p.interface_metrics_a = i.id
	and return_type in (select type from interface_metrics_types where class in (1, 2, 3))
	and id in 
		(
	  	select interface_metrics_id from interface_metrics_params
	  	where param in (select type from interface_metrics_types where class in (1, 2, 3))
	  	and   interface_metrics_id = i.id
		)
	union all
	select id from interface_metrics_pairs p, interface_metrics i
	where p.interface_metrics_b = i.id
	and return_type in (select type from interface_metrics_types where class in (1, 2, 3))
	and id in 
		(
	  	select interface_metrics_id from interface_metrics_params
	  	where param in (select type from interface_metrics_types where class in (1, 2, 3))
	  	and   interface_metrics_id = i.id
		)
	) t;
	

 -- #20 Pares de redundância semântica
select
        ((CASE WHEN a.result1 = b.result1 THEN 1 else 0 END) +
         (CASE WHEN a.result2 = b.result2 THEN 1 else 0 END) +
         (CASE WHEN a.result3 = b.result3 THEN 1 else 0 END) +
         (CASE WHEN a.result4 = b.result4 THEN 1 else 0 END)) as 'semantic',
       a.project_name as 'a.project_name',
       concat(a.modifiers,' ',a.return_type, ' ', a.fqn, a.params) a,
       b.project_name as 'b.project_name',
       concat(b.modifiers,' ',b.return_type, ' ', b.fqn, b.params) b,
       a.project_id as 'a.project_id',
       a.entity_id as 'a.entity_id',
       a.id as 'a.id',
       b.project_id as 'b.project_id',
       b.entity_id as 'b.entity_id',
       b.id as 'b.id',
       a.result1 as 'a.result1',
       a.result2 as 'a.result2',
       a.result3 as 'a.result3',
       a.result3 as 'a.result4',
       b.result1 as 'b.result1',
       b.result2 as 'b.result2',
       b.result3 as 'b.result3',
       b.result3 as 'b.result4',
       a.error as 'a.error',
       b.error as 'b.error'
from   interface_metrics_pairs p,
       interface_metrics a,
       interface_metrics b
where  a.id = p.interface_metrics_a
and    b.id = p.interface_metrics_b
and    p.search_type = 'p1_c1_w1_t1'
and    (a.error is not null or b.error is not null)
order by semantic DESC

/*
 #21 SCAM 2017
p = differentPackage; // True: Ignora os packages | False: Mesmos packages
c = ignoreClass;      // True: Ignora as Classes | False: Considera com Expansão Wordnet
w = expandMethodName; // True: Considera com Expansão Wordnet | False: Mesmos nomes de Métodos
t = expandTypes;      // True: Considera com Expansão de Tipos para o Retorno e Parâmetros | False: Mesmos Retorno e Parâmetros
*/

select '(1) Número de pares de métodos com interface similar s/ considerar o nome da classe' description, count(*) total
from   v_interface_metrics_pairs
where  search_type = 'p1_c1_w1_t1'
UNION all
select '  (1.a) Para quantos desses batem as quatro saídas para as quatro entradas (redundantes)' description, count(*) total
from   v_interface_metrics_compare
where  type = 'p1_c1_w1_t1'
and    result = 4
UNION all
select '  (1.b) Para quantos desses batem três saídas para as quatro entradas' description, count(*) total
from   v_interface_metrics_compare
where  type = 'p1_c1_w1_t1'
and    result = 3

UNION all
select '(2) Número de pares de métodos com interface similar considerando o nome da classe' description, count(*) total
from   v_interface_metrics_pairs
where  search_type = 'p1_c0_w1_t1'
UNION all
select '  (2.a) Para quantos desses batem as quatro saídas para as quatro entradas (redundantes)' description, count(*) total
from   v_interface_metrics_compare
where  type = 'p1_c0_w1_t1'
and    result = 4
UNION all
select '  (2.b) Para quantos desses batem três saídas para as quatro entradas' description, count(*) total
from   v_interface_metrics_compare
where  type = 'p1_c0_w1_t1'
and    result = 3
UNION all
select '(3) Qual é o número de métodos considerados no experimento (que casam com o filtro - estático, só tipos primitivos, etc.)?' description, count(*) total
from   interface_metrics i
where  return_type in (select type from interface_metrics_types where class in (1, 2, 3))
and    id in (
               select interface_metrics_id from interface_metrics_params
               where  param in (select type from interface_metrics_types where class in (1, 2, 3))
               and    interface_metrics_id = i.id
)
and project_type = 'CRAWLED'
UNION all
select '(4) Qual é o número de projetos considerados no experimento (que contém pelo menos um método que casa com o filtro - estático, só tipos primitivos, etc.)?' description, count(proj) total
from
(
select count(*) proj
from   interface_metrics i
where  return_type in (select type from interface_metrics_types where class in (1, 2, 3))
and    id in (
               select interface_metrics_id from interface_metrics_params
               where  param in (select type from interface_metrics_types where class in (1, 2, 3))
               and    interface_metrics_id = i.id
)
and    project_type = 'CRAWLED'
GROUP BY project_id
) t
UNION all
select '(7) Qual é o número de métodos redundantes (para os quais existe pelo menos outro método semelhante no qual batem as quatro saídas para as quatro entradas)?' description, count(*) total
from
(
select distinct a_id
from   v_interface_metrics_compare
where  result = 4
and    type = 'p1_c1_w1_t1'
) as t
UNION all
select '(8) Qual é o número de projetos que contém pelo menos um método redundante (método para o qual existe pelo menos outro método semelhante no qual batem as quatro saídas para as quatro entradas)' description, count(*) total
from
(
select a_project_id
from   v_interface_metrics_compare
where  result = 4
and    type = 'p1_c1_w1_t1'
group by a_project_id
) as t

 -- #22 SCAM 2017
 -- (5) Qual é o número de métodos considerados no experimento por projeto?
select project_type, project_id, project_name, count(*)
from   interface_metrics i
where  return_type in (select type from interface_metrics_types where class in (1, 2, 3))
and    id in (
               select interface_metrics_id from interface_metrics_params
               where  param in (select type from interface_metrics_types where class in (1, 2, 3))
               and    interface_metrics_id = i.id
)
and    project_type = 'CRAWLED'
GROUP BY project_type, project_id, project_name

 -- #23 SCAM 2017
 -- (6) Qual é o número de métodos no experimento que são redundantes (batem as quatro saídas para as quatro entradas) por projeto?
select a_project_type, a_project_id, a_project_name, count(*), result
from   v_interface_metrics_compare
where  result = 4
and    type = 'p1_c1_w1_t1'
GROUP BY a_project_type, a_project_id, a_project_name, result

 -- #24 SCAM 2017
 -- (9) Quais são os pares crawled-crawled para os quais batem as quatro saídas para as quatro entradas?
select *
from   v_interface_metrics_compare
where  result = 4
and    type = 'p1_c1_w1_t1'
and    b_project_type = 'CRAWLED'
