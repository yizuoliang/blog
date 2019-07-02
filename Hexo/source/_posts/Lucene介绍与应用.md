---
layout: posts
title: Lucene介绍与应用
date: 2019-06-06 00:38:36
categories: 全文检索技术
tags: [ Lucene]
---



# Lucene介绍与应用

GitHub仓库:<https://github.com/yizuoliang/blog/tree/master/Full-text%20Retrieval>

## 一、全文检索介绍

### 1.数据结构

#### 结构化数据：

​	指具有“固定格式” 或“限定长度”的数据； 

​	例如：数据库中的数据、元数据……

#### 非结构化数据

​	指不定长度或无固定格式的数据；

​	例如：文本、图片、视频、图表……

### 2.数据的搜索

#### 顺序扫描法

​	从第一个文件扫描到最后一个文件，把每一个文件内容从开头扫到结尾，直到扫完所有的文件。

#### 全文检索法

​	 将非结构化数据中的一部分信息提取出来，重新组织，建立索引，使其变得有一定结构，然后对此有一定结构的数据进行搜索，从而达到搜索相对较快的目的。

### 3.全文检索

例如：新华字典。字典的拼音表和部首检字表就相当于字典的索引，我们可以通过查找索引从而找到具体的字解释。如果没有创建索引，就要从字典的首页一页页地去查找。

这种先建立索引，再对索引进行搜索的过程就叫全文检索(Full-text Search) 。

#### 全文检索的核心

创建索引：将从所有的结构化和非结构化数据提取信息，创建索引的过程。

搜索索引：就是得到用户的查询请求，搜索创建的索引，然后返回结果的过程。

### 4.倒排索引

 倒排索引（英文：InvertedIndex），也称为反向索引，是一种索引方法，实现“单词-文档矩阵”的一种具体存储形式，常被用于存储在全文搜索下某个单词与文档的存储位置的映射，通过倒排索引，可以根据单词快速获取包含这个单词的文档列表。

倒排索引的结构主要由两个部分组成：“单词词典”和“倒排表”。

**索引方法例子**

​	3个文档内容为：

​		1.php是过去最流行的语言。

​		2.java是现在最流行的语言。

​		3.Python是未来流行的语言。

![1560995421919](Lucene介绍与应用/1560995421919.png)

#### 倒排索引的创建

​	1.使用分词系统将文档切分成单词序列，每个文档就成了由由单词序列构成的数据流；

​	2.给不同的单词赋予唯一的单词id,记录下对应的单词;

​	3.同时记录单词出现的文档,形成倒排列表。每个单词都指向了文档(Document)链表。

![1560995514416](Lucene介绍与应用/1560995514416.png)

#### 倒排索引的查询

​	假如说用户需要查询:   “现在流行”

​	1.将用户输入进行分词,分为”现在”和”流行”;

​	2.取出包含字符串“现在”的文档链表;

​	3.取出包含字符串“流行”的文档链表;

​	4.通过合并链表,找出包含有”现在”或者”流行”的链表。

#### 倒排索引原理

当然倒排索引的结构也不是上面说的那么简单，索引系统还可以记录除此之外的更多信息。词对应的倒排列表不仅记录了文档编号还记录了单词频率信息。词频信息在搜索结果时，是重要的排序依据。这里先了解下，后面的评分计算就要用到这个。

#### 索引和搜索流程图

![1560995688474](Lucene介绍与应用/1560995688474.png)

## 二、Lucene入门

**• Lucene是一套用于全文检索和搜寻的开源程序库，由Apache软件基金会支持和提供;**

**• 基于java的全文检索工具包, Lucene并不是现成的搜索引擎产品，但可以用来制作搜索引擎产品；**

**• 官网：http://lucene.apache.org/ 。**

### 1.Lucene的总体结构

![1560995814873](Lucene介绍与应用/1560995814873.png)

从lucene的总体架构图可以看出：

​    1.Lucene库提供了创建索引和搜索索引的API。

​     2.应用程序需要做的就是收集文档数据，创建索引；通过用户输入查询索引的得到返回结果。

### 2.Lucene的几个基本概念 

Index（索引）：类似数据库的表的概念，但它完全没有约束，可以修改和添加里面的文档，文档里的内容可以任意定义。

Document（文档）：类似数据库内的行的概念，一个Index内会包含多个Document。

Field（字段）：一个Document会由一个或多个Field组成，分词就是对Field 分词。

Term（词语）和Term Dictionary（词典）：Lucene中索引和搜索的最小单位，一个Field会由一个或多个Term组成，Term是由Field经过Analyzer（分词）产生。Term Dictionary即Term词典，是根据条件查找Term的基本索引。

![1560996749556](Lucene介绍与应用/1560996749556.png)

### 3.Lucene创建索引过程

Lucene创建索引过程如下：

1.创建一个IndexWriter用来写索引文件，它有几个参数，INDEX_DIR就是索引文件所存放的位置，Analyzer便是用来	对文档进行词法分析和语言处理的。

2.创建一个Document代表我们要索引的文档。将不同的Field加入到文档中。不同类型的信息用不同的Field来表示

3.IndexWriter调用函数addDocument将索引写到索引文件夹中。

### 4.Lucene搜索过程

搜索过程如下：

1.IndexReader将磁盘上的索引信息读入到内存，INDEX_DIR就是索引文件存放的位置。

2.创建IndexSearcher准备进行搜索。

3.创建Analyer用来对查询语句进行词法分析和语言处理。

4.创建QueryParser用来对查询语句进行语法分析。

5.QueryParser调用parser进行语法分析，形成查询语法树，放到Query中。

6.IndexSearcher调用search对查询语法树Query进行搜索，得到结果TopScoreDocCollector。

## 三、Lucene入门案例一

### 1.案例一代码

引入lucene的jar包

```
public class LuceneTest {

	public static void main(String[] args) throws Exception {
		// 1. 准备中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();
		// 2. 创建索引
		List<String> productNames = new ArrayList<>();
		productNames.add("小天鹅TG100-1420WDXG");
		productNames.add("小天鹅TB80-easy60W 洗漂脱时间自由可调，京东微联智能APP手机控制");
		productNames.add("小天鹅TG90-1411DG 洗涤容量:9kg 脱水容量:9kg 显示屏:LED数码屏显示");
		productNames.add("小天鹅TP75-V602 流线蝶形波轮，超强喷淋漂洗");
		productNames.add("小天鹅TG100V20WDG 大件洗，无旋钮外观，智能WiFi");
		productNames.add("小天鹅TD80-1411DG 洗涤容量:8kg 脱水容量:8kg 显示屏:LED数码屏显示");
		productNames.add("海尔XQB90-BZ828 洗涤容量:9kg 脱水容量:9kg 显示屏:LED数码屏显示");
		productNames.add("海尔G100818HBG 极简智控面板，V6蒸汽烘干，深层洁净");
		productNames.add("海尔G100678HB14SU1 洗涤容量:10kg 脱水容量:10kg 显示屏:LED数码屏显");
		productNames.add("海尔XQB80-KM12688 智能自由洗，超净洗");
		productNames.add("海尔EG8014HB39GU1 手机智能，一键免熨烫，空气净化洗");
		productNames.add("海尔G100818BG 琥珀金机身，深层洁净，轻柔雪纺洗");
		productNames.add("海尔G100728BX12G 安全磁锁，健康下排水");
		productNames.add("西门子XQG80-WD12G4C01W 衣干即停，热风除菌，低噪音");
		productNames.add("西门子XQG80-WD12G4681W 智能烘干，变速节能，无刷电机");
		productNames.add("西门子XQG100-WM14U568LW 洗涤容量:10kg 脱水容量:10kg 显示屏:LED");
		productNames.add("西门子XQG80-WM10N1C80W 除菌、洗涤分离，防过敏程序");
		productNames.add("西门子XQG100-WM14U561HW 洗涤容量:10kg 脱水容量:10kg 显示屏:LED");
		productNames.add("西门子XQG80-WM12L2E88W 洗涤容量:8kg 脱水容量:8kg 显示屏:LED触摸");
		Directory index = createIndex(analyzer, productNames);

		// 3. 查询器
		String keyword = "西门子 LED";
		Query query = new QueryParser("name", analyzer).parse(keyword);
		// 4. 搜索
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		int numberPerPage = 1000;
		System.out.printf("当前一共有%d条数据%n"+"<br>", productNames.size());
		System.out.printf("查询关键字是：\"%s\"%n"+"<br>", keyword);
		ScoreDoc[] hits = searcher.search(query, numberPerPage).scoreDocs;
		// 5. 显示查询结果
		showSearchResults(searcher, hits, query, analyzer);
		// 6. 关闭查询
		reader.close();
	}

	private static void showSearchResults(IndexSearcher searcher, ScoreDoc[] hits, Query query, IKAnalyzer analyzer)
			throws Exception {
		System.out.println("找到 " + hits.length + " 个命中.  <br>");
		System.out.println("序号\t匹配度得分\t结果  <br>");

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

		for (int i = 0; i < hits.length; ++i) {
			ScoreDoc scoreDoc= hits[i];
			int docId = scoreDoc.doc;
			Document d = searcher.doc(docId);
			List<IndexableField> fields = d.getFields();
			System.out.print((i + 1));
			System.out.print("\t" + scoreDoc.score);
			for (IndexableField f : fields) {
	            TokenStream tokenStream = analyzer.tokenStream(f.name(), new StringReader(d.get(f.name())));
	            String fieldContent = highlighter.getBestFragment(tokenStream, d.get(f.name()));				
				System.out.print("\t" + fieldContent);
			}
			System.out.println("<br>");
		}
	}


	private static Directory createIndex(IKAnalyzer analyzer, List<String> products) throws IOException {
		//存在内存中,新建一个词典
		Directory index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		for (String name : products) {
			addDoc(writer, name);
		}
		writer.close();
		return index;
	}
	
	/**
	 * 添加文档内容
	 * @param w
	 * @param name
	 * @throws IOException
	 */
	private static void addDoc(IndexWriter w, String name) throws IOException {
		//创建一个文档
		Document doc = new Document();
		doc.add(new TextField("name", name, Field.Store.YES));
		w.addDocument(doc);
	}
}
```



### 2.代码解析

#### 创建索引

```
private static Directory createIndex(IKAnalyzer analyzer, List<String> products) throws IOException {
    //存在内存中,新建一个词典
    Directory index = new RAMDirectory();
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter writer = new IndexWriter(index, config);
    for (String name : products) {
        addDoc(writer, name);
    }
    writer.close();
    return index;
}

private static void addDoc(IndexWriter w, String name) throws IOException {
    //创建一个文档
    Document doc = new Document();
    doc.add(new TextField("name", name, Field.Store.YES));
    w.addDocument(doc);
}

```

上面代码是将List中的内容保存在文档中，使用analyzer分词器分词，创建索引，索引保存在内存中。 IndexWriter 对象用来写索引的。

#### 查询索引

```
// 3. 查询器
String keyword = "西门子 智能";
Query query = new QueryParser("name", analyzer).parse(keyword);
// 4. 搜索
IndexReader reader = DirectoryReader.open(index);
IndexSearcher searcher = new IndexSearcher(reader);
int numberPerPage = 1000;
System.out.printf("当前一共有%d条数据%n", productNames.size());
System.out.printf("查询关键字是：\"%s\"%n", keyword);
ScoreDoc[] hits = searcher.search(query, numberPerPage).scoreDocs;
// 5. 显示查询结果
showSearchResults(searcher, hits, query, analyzer);
// 6. 关闭查询
reader.close();

```

上面代码是查询代码，首先对构建查询条件Query对象，读取索引，创建IndexSearcher 查询对象，传入查询条件，得到查询结果，将结果解析出来，返回。

#### 分词器

创建索引和查询都要用到分词器，在Lucene中分词主要依靠Analyzer类解析实现。Analyzer类是一个抽象类，分词的具体规则是由子类实现的，不同的语言规则，要有不同的分词器， Lucene默认的StandardAnalyzer是不支持中文的分词。

 代码中用到了IKAnalyzer分词器，IKAnalyzer是第三方实现的分词器，继承自Lucene的Analyzer类，针对中文文本进行处理的分词器。

#### 打分机制

   从案例返回结果来看,有一列匹配度得分,得分越高的排在越前面,排在前面的查询结果也越准确。

   打分公式：

![1560997114404](Lucene介绍与应用/1560997114404.png)

​       Lucene库也实现了上面的打分算法，查询结果也会根据分数进行排序。

#### 高亮显示

```
SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
```

将查询结果放到html页面，就会发现查询结果里关键字被标记为红色。在 Lucene库的org.apache.lucene.search.highlight包中提供了关于高亮显示检索关键字的方法，可以对返回的结果中出现了的关键字进行标记。

## 四、Lucene入门案例二

### 1.案例介绍

   1.将14万条商品详细信息到mysql数据库;

   2.使用Lucene库创建索引;

   3.使用Luncene查询索引,并做分页操作,得到返回查询到的数据,并记录查询时长;

   4.使用JDBC连接mysql数据库,采用like查询,对商品进行分页操作,返回查询到的数据,记录查询时长;

   5.比较mysql的模糊查询与Lucene全文检索查询。

### 2.案例二代码

引入lucene的jar包,和mysql的驱动包,创建数据库product表,插入数据.

```
/**
 * 商品bean类
 * @author yizl
 *
 */
public class Product {
	/**
	 * 商品id
	 */
	private int id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品类型
	 */
	private String category;
	/**
	 * 商品价格
	 */
	private float price;
	/**
	 * 商品产地
	 */
	private String place;
	/**
	 * 商品条形码
	 */
	private String code;
	
	......	
	
}

```

```
public class TestLucene {

	private static ProductDao dao = new ProductDao();

	public static void main(String[] args) throws Exception {
		// 1. 准备中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();
		// 2. 索引
		Directory index = createIndex(analyzer);
		// 3. 查询器
		Scanner s = new Scanner(System.in);

		while (true) {
			System.out.print("请输入查询关键字：");
			String keyword = s.nextLine();
			System.out.println("当前关键字是：" + keyword);
			long start = System.currentTimeMillis();
			// 查询名字字段
			Query query = new QueryParser("name", analyzer).parse(keyword);
			// 4. 搜索
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			ScoreDoc[] hits = pageSearch(query, searcher, 1, 10);
			// 5. 显示查询结果
			showSearchResults(searcher, hits, query, analyzer);
			// 6. 关闭查询
			reader.close();
			System.out.println("使用Lucene查询索引,耗时:" + (System.currentTimeMillis() - start) + "毫秒");

			System.out.println("-----------------------分割线-------------------------------");
			// 7.通过数据库进行模糊查询
			selectProductOfName(keyword);
		}
	}

	/**
	 * 通过mysql商品名查询
	 */
	private static void selectProductOfName(String str) {
		long start = System.currentTimeMillis();
		ResultBean<List<Product>> resultBean = dao.selectProductOfName(str, 1, 10);
		PageBean pageBean = resultBean.getPageBean();
		List<Product> products = resultBean.getData();
		System.out.println("查询出的总条数\t:" + pageBean.getTotal() + "条");
		System.out.println("当前第" + pageBean.getPageNow() + "页,每页显示" + pageBean.getPageSize() + "条数据");
		System.out.println("序号\t结果");
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			System.out.print((i + 1));
			System.out.print("\t" + product.getId());
			System.out.print("\t" + product.getName());
			System.out.print("\t" + product.getPrice());
			System.out.print("\t" + product.getPlace());
			System.out.print("\t" + product.getCode());
			System.out.println("<br>");
		}

		System.out.println("使用mysql查询,耗时:" + (System.currentTimeMillis() - start) + "毫秒");
	}

	/**
	 * 显示找到的结果
	 * 
	 * @param searcher
	 * @param hits
	 * @param query
	 * @param analyzer
	 * @throws Exception
	 */
	private static void showSearchResults(IndexSearcher searcher, ScoreDoc[] hits, Query query, IKAnalyzer analyzer)
			throws Exception {
		System.out.println("序号\t匹配度得分\t结果");
		for (int i = 0; i < hits.length; ++i) {
			ScoreDoc scoreDoc = hits[i];
			int docId = scoreDoc.doc;
			Document d = searcher.doc(docId);
			List<IndexableField> fields = d.getFields();
			System.out.print((i + 1));
			System.out.print("\t" + scoreDoc.score);
			for (IndexableField f : fields) {
				System.out.print("\t" + d.get(f.name()));
			}
			System.out.println("<br>");
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @param searcher
	 * @param pageNow
	 *            当前第几页
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 * @throws IOException
	 */
	private static ScoreDoc[] pageSearch(Query query, IndexSearcher searcher, int pageNow, int pageSize)
			throws IOException {
		TopDocs topDocs = searcher.search(query, pageNow * pageSize);
		System.out.println("查询到的总条数\t" + topDocs.totalHits);
		System.out.println("当前第" + pageNow + "页,每页显示" + pageSize + "条数据");
		ScoreDoc[] alllScores = topDocs.scoreDocs;
		List<ScoreDoc> hitScores = new ArrayList<>();

		int start = (pageNow - 1) * pageSize;
		int end = pageSize * pageNow;
		for (int i = start; i < end; i++)
			hitScores.add(alllScores[i]);

		ScoreDoc[] hits = hitScores.toArray(new ScoreDoc[] {});
		return hits;
	}

	/**
	 * 创建Index,将数据存入内存中
	 * 
	 * @param analyzer
	 * @return
	 * @throws IOException
	 */
	private static Directory createIndex(IKAnalyzer analyzer) throws IOException {
		long start = System.currentTimeMillis();
		Directory index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		List<Product> products = dao.selectAllProduct();
		int total = products.size();
		int count = 0;
		int per = 0;
		int oldPer = 0;
		for (Product p : products) {
			addDoc(writer, p);
			count++;
			per = count * 100 / total;
			if (per != oldPer) {
				oldPer = per;
				System.out.printf("索引中，总共要添加 %d 条记录，当前添加进度是： %d%% %n", total, per);
			}

		}
		System.out.println("索引创建耗时:" + (System.currentTimeMillis() - start) + "毫秒");
		writer.close();
		return index;
	}

	/**
	 * 往lucene中添加字段
	 * 
	 * @param w
	 * @param p
	 * @throws IOException
	 */
	private static void addDoc(IndexWriter w, Product p) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("id", String.valueOf(p.getId()), Field.Store.YES));
		doc.add(new TextField("name", p.getName(), Field.Store.YES));
		doc.add(new TextField("category", p.getCategory(), Field.Store.YES));
		doc.add(new TextField("price", String.valueOf(p.getPrice()), Field.Store.YES));
		doc.add(new TextField("place", p.getPlace(), Field.Store.YES));
		doc.add(new TextField("code", p.getCode(), Field.Store.YES));
		w.addDocument(doc);
	}
}
```



```
public class ProductDao {

	private static String url = "jdbc:mysql://localhost:3306/lucene?useUnicode=true&characterEncoding=utf8";
	private static String user = "root";
	private static String password = "root";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		// 通过工具类获取连接对象
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	/**
	 * 批量增加商品
	 * @param pList
	 */
	public void insertProduct(List<Product> pList) {
		String insertProductTop="INSERT INTO `product` (`id`, `name`, "
				+ "`category`, `price`, `place`, `code`) VALUES ";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			// 3.创建Statement对象
			stmt = conn.createStatement();
			int count=0;
			// 4.sql语句
			StringBuffer sb = new StringBuffer();
			for (int i = 0,len=pList.size(); i < len; i++) {
				Product product = pList.get(i);
				sb.append("(" + product.getId() + ",'" + product.getName() 
				+ "','" + product.getCategory()+ "'," + product.getPrice()
				+ ",'" + product.getPlace() + "','" + product.getCode() + "')");
				if (i==len-1) {
					sb.append(";");
					break;
				}else {
					sb.append(",");
				}
				//数据量太大会导致一次执行不了,一次最多执行20000条
				if(i%20000==0&&i!=0) {
					sb.deleteCharAt(sb.length()-1);
					sb.append(";");
					String sql = insertProductTop+sb;
					count += stmt.executeUpdate(sql);
					//将sb清空
					sb.delete(0, sb.length());
				}
			}
			
			String sql = insertProductTop+sb;
			// 5.执行sql
			count += stmt.executeUpdate(sql);
			System.out.println("影响了" + count + "行");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(conn, stmt);
		}
	}
	
	/**
	 * 关闭资源
	 * @param conn
	 * @param stmt
	 */
	private void close(Connection conn, Statement stmt) {
		// 关闭资源
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	// 
	public void deleteAllProduct() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			// 3.创建Statement对象
			stmt = conn.createStatement();
			// 4.sql语句
			String sql = "delete from product";
			// 5.执行sql
			int count = stmt.executeUpdate(sql);
			System.out.println("影响了" + count + "行");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			close(conn, stmt);
		}
	}

	/**
	 * 查询所有商品
	 */
	public List<Product> selectAllProduct() {
		List<Product> pList=new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			// 3.创建Statement对象
			stmt = conn.createStatement();
			// 4.sql语句
			String sql = "select * from product";
			// 5.执行sql
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Product product=new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPlace(rs.getString("place"));
				product.setPrice(rs.getFloat("price"));
				product.setCode(rs.getString("code"));
				pList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			close(conn, stmt);
		}
		return pList;
	}
	/**
	 * 通过商品名模糊匹配商品
	 * @param strName
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public ResultBean<List<Product>> selectProductOfName(String strName, int pageNow, int pageSize) {
		ResultBean<List<Product>> resultBean=new ResultBean<List<Product>>();
		PageBean pageBean =new PageBean();
		pageBean.setPageNow(pageNow);
		pageBean.setPageSize(pageSize);
		List<Product> pList=new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			// sql语句
			String sql = "SELECT id,name,category,place,price,code FROM product"
	                + " where name like ? limit "+(pageNow-1)*pageSize+","+pageSize; 
			// 3.创建PreparedStatement对象,sql预编译
			pstmt = conn.prepareStatement(sql);
			// 4.设定参数
			pstmt.setString(1, "%" + strName + "%" );                  
			// 5.执行sql,获取查询的结果集  
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product=new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPlace(rs.getString("place"));
				product.setPrice(rs.getFloat("price"));
				product.setCode(rs.getString("code"));
				pList.add(product);
			}
			
			String selectCount = "SELECT count(1) c FROM product"
	                + " where name like ? ";
			pstmt = conn.prepareStatement(selectCount);
			pstmt.setString(1, "%" + strName + "%" ); 
			ResultSet rs1 = pstmt.executeQuery();
			int count=0;
			while (rs1.next()) {
				count = rs1.getInt("c");
			}
			pageBean.setTotal(count);
			resultBean.setPageBean(pageBean);
			resultBean.setData(pList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return resultBean;
	
	}
}
```

```
/**
 * 返回结果bean
 * @author yizl
 *
 * @param <T>
 */
public class ResultBean<T> {
	
	/**
	 * 分页信息
	 */
	private PageBean pageBean;
	
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
	private T data;
	
```

```
/**
 * 分页bean
 * @author yizl
 *
 */
public class PageBean {
    /**
     * 当前页数
     */
    private Integer pageNow;

    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer total;
    
```



### 4.Lucene的分页查询

```
private static ScoreDoc[] pageSearch(Query query, IndexSearcher searcher, int pageNow, int pageSize)
throws IOException {
TopDocs topDocs = searcher.search(query, pageNow * pageSize);
System.out.println("查询到的总条数\t" + topDocs.totalHits);
System.out.println("当前第" + pageNow + "页,每页显示" + pageSize + "条数据");
ScoreDoc[] alllScores = topDocs.scoreDocs;
List<ScoreDoc> hitScores = new ArrayList<>();

int start = (pageNow - 1) * pageSize;
int end = pageSize * pageNow;
for (int i = start; i < end; i++)
hitScores.add(alllScores[i]);
ScoreDoc[] hits = hitScores.toArray(new ScoreDoc[] {});
return hits;
}
```

先把所有的命中数查询出来，在进行分页，有点是查询快，缺点是内存消耗大。

### 5.结果比较分析

1.14万条数据,从创建lucene索引耗时:11678毫秒,创建索引还是比较耗时的,但是索引只用创建一次,后面都查询都可以使用；
2.从查询时间来看,使用Lucene查询,基本都在10ms左右,mysql查询耗时在150ms以上,查询速度方面有很大的提升，特别是数据量大的时候更加明显；

3.从查询精准度来说，输入单个的词语可能都能查询到结果，输入组合词语，mysql可以匹配不了，Lucene依然可以查询出来，将匹配度高的结果排在前面，更精准。

### 6.Lucene索引与mysql数据库对比

|            | Lucene全文检索                                          | mysql数据库                                                  |
| ---------- | ------------------------------------------------------- | ------------------------------------------------------------ |
| 索引       | 将数据源中的数据--建立反向索引,查询快                   | 对于like查询来说,传统数据库的索引不起作用,还是要全表扫描，查询慢 |
| 匹配效果   | 词元(term)匹配,通过语言分析接口进行关键字拆分，匹配度高 | 模糊匹配,可能不能匹配相关的词组                              |
| 匹配度     | 有匹配度算法,匹配度高的得分高排前面                     | 无匹配程度算法,随机排列                                      |
| 关键字标记 | 提供高亮显示的Api,可以对查询结果的关键字高亮标记        | 没有直接使用的api,需要自己封装                               |

## 五、总结

首先我们了解全文检索方法，全文检索搜索非结构化数据速度快等优点，倒排索引是现在最常用的全文检索方法，索引的核心就是怎么创建索引和查询索引。至于怎么实现创建和查询，Apache软件基金会很贴心的为我们Java程序员提供了Lucene开源库，它为我们提供了创建和查询索引的api，这就是我们学习Lucene的目的。