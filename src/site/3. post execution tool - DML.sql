-- **************************
 -- interface_metrics_filter
-- **************************
delete from interface_metrics_filter;

insert into interface_metrics_filter
select 'ALL entity_type' as 'entities Filter', count(*) Total from entities;

insert into interface_metrics_filter
select '1) entity_type = METHOD' as 'entities Filter', count(*) Total from entities
where entity_type = 'METHOD';

insert into interface_metrics_filter
select '1) 2) modifiers like %PUBLIC%' as 'entities Filter', count(*) Total from entities
where entity_type = 'METHOD'
and modifiers like '%PUBLIC%';

insert into interface_metrics_filter
select '1) 2) 3) modifiers not like %ABSTRACT%' as 'entities Filter', count(*) Total from entities
where entity_type = 'METHOD'
and modifiers like '%PUBLIC%'
and modifiers not like '%ABSTRACT%';

insert into interface_metrics_filter
select '1) 2) 3) 4) params <> ()' as 'entities Filter', count(*) Total from entities
where entity_type = 'METHOD'
and modifiers like '%PUBLIC%'
and modifiers not like '%ABSTRACT%'
and params <> '()';

insert into interface_metrics_filter
select '1) 2) 3) 4) 5) return <> void' as 'entities Filter', count(*) Total from entities e1
,entities e2
,relations r
, projects p
where e1.entity_id = r.lhs_eid
and r.rhs_eid = e2.entity_id
and r.relation_type = 'RETURNS'
and e1.project_id = p.project_id
and e1.entity_type = 'METHOD'
and e1.modifiers like '%PUBLIC%'
and e1.modifiers not like '%ABSTRACT%'
and e1.params <> '()'
and e2.fqn <> 'void';

insert into interface_metrics_filter
select '1) 2) 3) 4) 5) 6) and modifiers like %STATIC%' as 'entities Filter', count(*) Total from entities e1
,entities e2
,relations r
, projects p
where e1.entity_id = r.lhs_eid
and r.rhs_eid = e2.entity_id
and r.relation_type = 'RETURNS'
and e1.project_id = p.project_id
and e1.entity_type = 'METHOD'
and e1.modifiers like '%PUBLIC%'
and e1.modifiers not like '%ABSTRACT%'
and e1.params <> '()'
and e2.fqn <> 'void'
and e1.modifiers like '%STATIC%';


-- **************************
 -- interface_metrics_semantics_ids
-- **************************
update interface_metrics_params
set is_class_123 = 1
where param in (select type from interface_metrics_types where class in (1, 2, 3));

update interface_metrics_params
set is_class_123 = 0
where (is_class_123 <> 1 or is_class_123 is null);

update interface_metrics set is_semantics_execution = null;

update interface_metrics
set is_semantics_execution = -1
where return_type in (select type from interface_metrics_types where class in (1, 2, 3));

update interface_metrics i
set is_semantics_execution = 0
where is_semantics_execution = -1
and i.id in (select interface_metrics_id from interface_metrics_params
	  	       where  interface_metrics_id = i.id
	  	       and    is_class_123 = 0);

update interface_metrics
set is_semantics_execution = 1
where is_semantics_execution = -1;

delete from interface_metrics_semantics_ids;

insert into interface_metrics_semantics_ids
select distinct t.id 
 from
 	(
	select id from interface_metrics_pairs p, interface_metrics i
	where p.interface_metrics_a = i.id
	and i.is_semantics_execution = 1
	union all
	select id from interface_metrics_pairs p, interface_metrics i
	where p.interface_metrics_b = i.id
	and i.is_semantics_execution = 1
	) t;

