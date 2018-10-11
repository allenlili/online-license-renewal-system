package comp9322.ds.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import comp9322.ds.entity.LicenceClassType;
import comp9322.ds.service.QueryService;
import comp9322.ds.service.impl.QueryServiceImpl;

@Path("/class-and-type")
public class QueryController {
	private QueryService queryService;
	

	@GET
	@Path("/echo/{input}")
	@Produces("text/plain")
	public String ping(@PathParam("input") String input) {
		return input;
	}

	@GET
	@Path("/{subpath:|xml}")
	@Produces({MediaType.APPLICATION_XML})
	public Response queryReturnTable(@QueryParam("select") String select, @QueryParam("filter") String where, @QueryParam("orderby") String orderby) {
		ResponseBuilder builder = null;
		queryService = getQueryService();
		LicenceClassType licenceClassType = null;
		try {
			licenceClassType = queryService.queryLicenceClassTypeReturnXML(select, where, orderby);
		} catch (Exception e) {
			e.printStackTrace();
			String content = null;
			try {
				content = queryService.errorHandle("Data cannot be loaded for your request.");
			} catch (Exception e1) {
				e1.printStackTrace();
				builder = Response.ok("Internal Error.").status(501);
				return builder.build();
			}
			builder = Response.ok(content).status(500);
			return builder.build();
		}
		builder = Response.ok(licenceClassType.getData());
		builder.link("/class-and-type/json" + getQueryParams(select, where, orderby), "JSON");
		builder.link("/class-and-type/html" + getQueryParams(select, where, orderby), "HTML");
		return builder.build();
	}
	
	
	@GET
	@Path("/json")
	@Produces({MediaType.APPLICATION_JSON})
	public Response queryReturnJson(@QueryParam("select") String select, @QueryParam("filter") String where, @QueryParam("orderby") String orderby) {
		ResponseBuilder builder = null;
		queryService = getQueryService();
		LicenceClassType licenceClassType = null;
		try {
			licenceClassType = queryService.queryLicenceClassTypeReturnXML(select, where, orderby);
		} catch (Exception e) {
			e.printStackTrace();
			String content = null;
			try {
				content = queryService.errorHandle("Data cannot be loaded for your request.");
			} catch (Exception e1) {
				e1.printStackTrace();
				builder = Response.ok("Internal Error.").status(501);
				return builder.build();
			}
			builder = Response.ok(content).status(500);
			return builder.build();
		}
		licenceClassType.setData(xmlToJson(licenceClassType.getData()));
		builder = Response.ok(licenceClassType.getData());
		builder.link("/class-and-type" + getQueryParams(select, where, orderby), "XML");
		builder.link("/class-and-type/xml" + getQueryParams(select, where, orderby), "XML");
		builder.link("/class-and-type/html" + getQueryParams(select, where, orderby), "HTML");
		return builder.build();
	}
	
	@GET
	@Path("/html")
	@Produces({MediaType.TEXT_HTML})
	public Response queryReturnHTML(@QueryParam("select") String select, @QueryParam("filter") String where, @QueryParam("orderby") String orderby) {
		ResponseBuilder builder = null;
		queryService = getQueryService();
		LicenceClassType licenceClassType = null;
		try {
			licenceClassType = queryService.queryLicenceClassTypeReturnHTML(select, where, orderby);
		} catch (Exception e) {
			e.printStackTrace();
			String content = null;
			try {
				content = queryService.errorHandle("Data cannot be loaded for your request.");
			} catch (Exception e1) {
				e1.printStackTrace();
				builder = Response.ok("Internal Error.").status(501);
				return builder.build();
			}
			builder = Response.ok(content).status(500);
			return builder.build();
		}
		builder = Response.ok(licenceClassType.getData());
		builder.link("/class-and-type" + getQueryParams(select, where, orderby), "XML");
		builder.link("/class-and-type/xml" + getQueryParams(select, where, orderby), "XML");
		builder.link("/class-and-type/json" + getQueryParams(select, where, orderby), "JSON");
		return builder.build();
	}
	
	private QueryService getQueryService() {
		if (queryService == null) {
			return new QueryServiceImpl();
		}
		return queryService;
	}
	
	private static String xmlToJson(String xml) {
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String jsonPrettyPrintString = null;
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xml);
            jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return jsonPrettyPrintString;
    }
	
	private String getQueryParams(String select, String where, String orderby) {
		StringBuffer stringBuffer = new StringBuffer();
		if (select != null) {
			stringBuffer.append("&select=" + select.replace(" ", "+"));
		}
		if (where != null) {
			if (where.contains("<=")) {
				where = where.replace("<=", "le");
			} else if (where.contains("<")) {
				where = where.replace("<", "lt");
			}
			if (where.contains(">=")) {
				where = where.replace(">=", "ge");
			} else if (where.contains(">")) {
				where = where.replace(">", "gt");
			}
			stringBuffer.append("&filter=" + where.replace(" ", "+"));
		}
		if (orderby != null) {
			stringBuffer.append("&orderby=" + orderby.replace(" ", "+"));
		}
		if (stringBuffer.toString() != "") {
			stringBuffer.insert(0, "?");
		}
		return stringBuffer.toString();
	}
}
