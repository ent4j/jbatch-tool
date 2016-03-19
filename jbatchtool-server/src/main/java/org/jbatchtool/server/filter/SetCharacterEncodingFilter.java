package org.jbatchtool.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 文字コード設定フィルタクラス。<br>
 */
public class SetCharacterEncodingFilter implements Filter {

	/**
	 * 文字コード
	 */
	protected String encoding = null;

	/**
	 * フィルタコンフィグ
	 */
	protected FilterConfig filterConfig = null;

	/**
	 * リクエストから送られてきたエンコーディングを無効とするかどうかのフラグ
	 */
	protected boolean ignore = true;

	/**
	 * 停止メソッド。<br>
	 * クラス破棄時に呼ばれる。
	 */
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	/**
	 * フィルタ実行メソッド。<br>
	 * リクエストに対し、文字コードのフィルタリングを行う。
	 *
	 * @param request リクエスト
	 * @param response レスポンス
	 * @param chain フィルタチェイン
	 * @exception IOException 入出力関係の例外発生時
	 * @exception ServletException サーブレット関係の例外発生時
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if (encoding != null)
				request.setCharacterEncoding(encoding);
		}

		chain.doFilter(request, response);
	}

	/**
	 * 開始メソッド。<br>
	 * クラス生成時に呼ばれる。
	 *
	 * @param filterConfig フィルタコンフィグ
	 * @exception ServletException サーブレット関係の例外発生時
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	/**
	 * 文字コード取得メソッド。<br>
	 * 設定されている文字コードを取得する。
	 *
	 * @param request リクエスト
	 * @return 文字コード
	 */
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

}
