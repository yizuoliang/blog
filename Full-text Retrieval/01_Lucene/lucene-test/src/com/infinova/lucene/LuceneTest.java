package com.infinova.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

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
