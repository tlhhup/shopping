<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery-autocompleter/jquery.autocompleter.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/es/elasticsearch.jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/es/elasticsearch.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-autocompleter/jquery.autocompleter.min.js" type="text/javascript"></script>

<script type="text/javascript">
	var client;
	var product;
	$(function(){
		//初始化es对象
		client = new $.es.Client({
			  hosts: 'localhost:9200'
		});
		
		client.ping({
			  requestTimeout : 30000,
		}, function(error) {
			if (error) {
				console.error('elasticsearch cluster is down!');
			} else {
				console.log('All is well');
			}
		});
		//初始化自动补全
		product=$('#product').autocompleter({
	        limit: 5,
	        cache: true
		});
	});
	
	function change(param){
		var value=param.value;
		if(value!=null && value!=''){
			client.search({
				index:'shopping',
				body:{
					query:{
						match:{
							name:value
						}
					}
				}
			},function(error,response){
				var hits=response.hits.hits;//查询之后的数据
				var result=[];
				var item;
				$.each(hits,function(index,data){
					item=new Object();
					item.label=data._source.name;
					result[index]=item;
				});
				//重新绑定数据
				if(result.length>0){
					console.info(result);
					$('#product').autocompleter({ source: result });
				}
			})
		}
	}
</script>
<title>自动补全</title>
</head>
<body>
	通过ES提供的suggest的completion处理输入过的数据自动补全：将输入过的数据添加到索引中，下一次根据输入从索引中取出展示<br>
	<br>
	请输入商品名称：<input id="product" type="text" placeholder="请输出商品名称" oninput="change(this)">
</body>
</html>