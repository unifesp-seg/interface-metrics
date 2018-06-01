 -- #20 Pares de redundância semântica
select
       result as 'semantic',
       a_project_name,
       a_fqn,
       b_project_name,
       b_fqn,
       a_project_id,
       a_entity_id,
       a_id,
       b_project_id,
       b_entity_id,
       b_id,
       a_result1,
       a_result2,
       a_result3,
       a_result4,
       b_result1,
       b_result2,
       b_result3,
       b_result4,
       a_error,
       b_error
from   v_interface_metrics_pairs
where  search_type = 'p1_c1_w1_t1'
and    (a_error is not null or b_error is not null)
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
from   v_interface_metrics_pairs
where  search_type = 'p1_c1_w1_t1'
and    result = 4
UNION all
select '  (1.b) Para quantos desses batem três saídas para as quatro entradas' description, count(*) total
from   v_interface_metrics_pairs
where  search_type = 'p1_c1_w1_t1'
and    result = 3

UNION all
select '(2) Número de pares de métodos com interface similar considerando o nome da classe' description, count(*) total
from   v_interface_metrics_pairs
where  search_type = 'p1_c0_w1_t1'
UNION all
select '  (2.a) Para quantos desses batem as quatro saídas para as quatro entradas (redundantes)' description, count(*) total
from   v_interface_metrics_pairs
where  search_type = 'p1_c0_w1_t1'
and    result = 4
UNION all
select '  (2.b) Para quantos desses batem três saídas para as quatro entradas' description, count(*) total
from   v_interface_metrics_pairs
where  search_type = 'p1_c0_w1_t1'
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
from   v_interface_metrics_pairs
where  result = 4
and    search_type = 'p1_c1_w1_t1'
) as t
UNION all
select '(8) Qual é o número de projetos que contém pelo menos um método redundante (método para o qual existe pelo menos outro método semelhante no qual batem as quatro saídas para as quatro entradas)' description, count(*) total
from
(
select a_project_id
from   v_interface_metrics_pairs
where  result = 4
and    search_type = 'p1_c1_w1_t1'
group by a_project_id
) as t

 -- #22 SCAM 2017
 -- (5) Qual é o número de métodos considerados no experimento por projeto?
select project_type, project_id, project_name, count(*) total
from   interface_metrics i
where  return_type in (select type from interface_metrics_types where class in (1, 2, 3))
and    id in (
               select interface_metrics_id from interface_metrics_params
               where  param in (select type from interface_metrics_types where class in (1, 2, 3))
               and    interface_metrics_id = i.id
)
and    project_type = 'CRAWLED'
GROUP BY project_type, project_id, project_name
order by total desc, project_name

 -- #23 SCAM 2017
 -- (6) Qual é o número de métodos no experimento que são redundantes (batem as quatro saídas para as quatro entradas) por projeto?
select a_project_type, a_project_id, a_project_name, count(*) total, result
from   v_interface_metrics_pairs
where  result = 4
and    search_type = 'p1_c1_w1_t1'
GROUP BY a_project_type, a_project_id, a_project_name, result
order by a_project_id

 -- #24 SCAM 2017
 -- (9) Quais são os pares crawled-crawled para os quais batem as quatro saídas para as quatro entradas?
select 
       result as 'semantic',
       a_project_name,
       a_fqn,
       b_project_name,
       b_fqn,
       a_project_id,
       a_entity_id,
       a_id,
       b_project_id,
       b_entity_id,
       b_id,
       a_result1,
       a_result2,
       a_result3,
       a_result4,
       b_result1,
       b_result2,
       b_result3,
       b_result4,
       a_error,
       b_error
from   v_interface_metrics_pairs
where  result = 4
and    search_type = 'p1_c1_w1_t1'
and    b_project_type = 'CRAWLED'
order by a_error, a_project_id

-- #25 SCAM 2017
 -- (10) Qual é o número de métodos no experimento que são redundantes em 1 (batem pelo menos uma das saídas para as mesmas entradas) por projeto?
select a_project_type, a_project_id, a_project_name, count(*) total
from   v_interface_metrics_pairs
where  result >= 1
and    search_type = 'p1_c1_w1_t1'
GROUP BY a_project_type, a_project_id, a_project_name
order by a_project_id
