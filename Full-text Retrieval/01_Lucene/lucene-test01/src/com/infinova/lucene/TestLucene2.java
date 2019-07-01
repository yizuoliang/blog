package com.infinova.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import org.apache.lucene.search.TopDocs;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.infinova.lucene.bean.PageBean;
import com.infinova.lucene.bean.Product;
import com.infinova.lucene.bean.ResultBean;
import com.infinova.lucene.dao.ProductDao;

public class TestLucene2 {

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
		for (int i = start; i < end; i++) {
			hitScores.add(alllScores[i]);
		}
		ScoreDoc[] hits = hitScores.toArray(new ScoreDoc[] {});
		return hits;
	}

	/**
	 * 创建Index,将数据存入硬盘中
	 * 
	 * @param analyzer
	 * @return
	 * @throws IOException
	 */
	private static Directory createIndex(IKAnalyzer analyzer) throws IOException {
		long start = System.currentTimeMillis();
		Path path = Paths.get("indexDir/");
		System.out.println(path.toRealPath());
		Directory index = FSDirectory.open(Paths.get("indexDir/"));
		
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(index, config);
 		List<Product> products = dao.selectAllProduct();
		ArrayList<Document> docs=new ArrayList<Document>();
		for (Product p : products) {
			addDoc(writer, p,docs);
		}
		writer.addDocuments(docs);
		//writer.commit();
		System.out.println("索引创建耗时:" + (System.currentTimeMillis() - start) + "毫秒");
		writer.close();
		return index;
	}

	/**
	 * 往lucene中添加字段
	 * 
	 * @param w
	 * @param p
	 * @param docs 
	 * @throws IOException
	 */
	private static void addDoc(IndexWriter w, Product p, ArrayList<Document> docs) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("id", String.valueOf(p.getId()), Field.Store.YES));
		doc.add(new TextField("name", p.getName(), Field.Store.YES));
		doc.add(new TextField("category", p.getCategory(), Field.Store.YES));
		doc.add(new TextField("price", String.valueOf(p.getPrice()), Field.Store.YES));
		doc.add(new TextField("place", p.getPlace(), Field.Store.YES));
		doc.add(new TextField("code", p.getCode(), Field.Store.YES));
		//w.addDocument(doc);
		docs.add(doc);
	}
}
