package com.garlane.relation.common.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.common.servlet.uitl.TokenUtil;

/**
 * @author linhx
 *
 */
public class TokenServlet extends HttpServlet{
	private static final long serialVersionUID = 8713634959941852995L;
	static final String FILE_NAME_FIELD = "name";
	static final String FILE_PATH_FIELD = "customSavePath";
	static final String FILE_SIZE_FIELD = "size";
	static final String TOKEN_FIELD = "token";
	static final String SERVER_FIELD = "server";
	static final String SUCCESS = "success";
	static final String MESSAGE = "message";
	static final String FILE_UUID_NAME_FIELD = "uuidFileName";
	
	@Override
	public void init() throws ServletException {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String name = req.getParameter(FILE_NAME_FIELD);
		String size = req.getParameter(FILE_SIZE_FIELD);
		String token = TokenUtil.generateToken(name, size);
		
		PrintWriter writer = resp.getWriter();
		
		JSONObject json = new JSONObject();
		try {
			json.put(TOKEN_FIELD, token);
			//if (Configurations.isCrossed())
				//json.put(SERVER_FIELD, Configurations.getCrossServer());
			json.put(SUCCESS, true);
			json.put(MESSAGE, "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/** TODO: save the token. */
		
		writer.write(json.toString());
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doHead(req, resp);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
