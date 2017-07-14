package cn.zdsoft.packager;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import cn.zdsoft.packager.config.FilePackager;

public class AppTest {

	/**
	 * 测试文件列表
	 */
	@Test
	public void testFileList() {
		File file = new File("F:\\迅雷下载");
		File[] fs = file.listFiles();
		List<File> listFile = Arrays.asList(fs);

		// 排序，文件夹在前面，文件在后面。升序
		listFile.sort(new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				// 返回f1和f2的asc码的差值。
				if (f1.isDirectory() && f2.isFile())
					return -1;//
				if (f1.isFile() && f2.isDirectory())
					return 1;

				return f1.getName().compareTo(f2.getName());
				// return f2.getName().compareTo(f1.getName());
			}
		});

		// 打印
		listFile.forEach(f -> System.out.println(f.getName()));

		String a = "a";
		String b = "f";
		System.out.println(a.compareTo(b));
	}

	@Test
	public void testSer() {
		FilePackager fp = new FilePackager();
		fp.Name = "123";
		System.out.println(new Gson().toJson(fp));
	}

}
