package ru.ncedu.graph.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import ru.ncedu.graph.logic.Edge;
import ru.ncedu.graph.logic.GraphManager;
import ru.ncedu.graph.logic.RandomDataGenerator;
import ru.ncedu.graph.logic.Path;
import ru.ncedu.graph.logic.Vertex;

@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {
	/* admin/user section variables */
	private boolean isAdmin = false;
	private final String adminName = "admin";
	private final String adminPassword = "admin";
	private String username;
	private String password;

	/* path-builder section variables */
	private HorizontalBarChartModel path;
	private Vertex selectedVertex; // selected from index.xhtml table
	private Edge selectedDestination;
	private int currentVertexId;
	private int start;
	private int finish;
	private double distance;
	public int size;
	private List<Edge> edges;

	/* dijkstra section variables */
	// start and destination to build closest path between them
	private int dijkstraStart;
	private int dijkstraDestination;
	// rendered path
	private HorizontalBarChartModel renderedPath;
	// what to render
	private Path dijkstraPath;
	// info on click
	private String startInfo;
	private String finishInfo;
	private double distanceInfo;

	// vertex add
	private int vertexIdToAdd;
	private double vertexDelayToAdd;
	// vertex delete
	private int vertexIdToDelete;
	// edge add
	private int startAdd;
	private int finishAdd;
	private double range;
	// edge delete
	private int startDelete;
	private int finishDelete;

	/* admin/user section */
	public void login() {
		if (username.equals(adminName) && password.equals(adminPassword)) {
			this.isAdmin = true;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Success!", "You have logged as admin");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			this.username = null;
			this.password = null;
		} else
			FacesContext.getCurrentInstance().addMessage(
					"login",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Access denied!", ""));
		// return "Refresh";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void logout() {
		this.isAdmin = false;
	}

	/* path-builder section */
	// jump to 'path-builder.xhtml

	public String buildPath() {
		GraphManager gt = new RandomDataGenerator();
		this.edges = gt.getEdges(selectedVertex.getId());
		init();
		return "Build";
	}

	public void init() {
		this.start = 0;
		this.finish = 0;
		this.distance = 0;
		this.size = 0;
		this.path = new HorizontalBarChartModel();
		ChartSeries cs = new ChartSeries();
		cs.set("Your path", 0);
		cs.setLabel(Integer.toString(selectedVertex.getId()));
		this.path.addSeries(cs);
		this.path.setStacked(true);
		this.currentVertexId = selectedVertex.getId();
	}

	public void addElement() {
		GraphManager gt = new RandomDataGenerator();
		this.edges = gt.getEdges(0);
		ChartSeries cs = new ChartSeries();
		cs.setLabel(Integer.toString(selectedDestination.getId2()));
		cs.set("Your path", selectedDestination.getDistance());
		this.path.addSeries(cs);
		this.size += selectedDestination.getDistance();
		this.currentVertexId = selectedDestination.getId2();
		this.setAxis();
	}

	private void setAxis() {
		Axis xAxis = path.getAxis(AxisType.X);
		xAxis.setMin(0);
		xAxis.setMax(size + size / 14);
	}

	public void showDetails(ItemSelectEvent event) {
		int index = event.getSeriesIndex();
		this.start = Integer.parseInt(path.getSeries().get(index - 1)
				.getLabel());
		this.finish = Integer.parseInt(path.getSeries().get(index).getLabel());
		this.distance = path.getSeries().get(index).getData().get("Your path")
				.intValue();
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Edge getSelectedDestination() {
		return selectedDestination;
	}

	public void setSelectedDestination(Edge selectedDestination) {
		this.selectedDestination = selectedDestination;
	}

	public Vertex getSelectedVertex() {
		return selectedVertex;
	}

	public void setSelectedVertex(Vertex selectedVertex) {
		this.selectedVertex = selectedVertex;
	}

	public int getCurrentVertexId() {
		return currentVertexId;
	}

	public void setCurrentVertexId(int currentVertexId) {
		this.currentVertexId = currentVertexId;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public HorizontalBarChartModel getPath() {
		return path;
	}

	public void setPath(HorizontalBarChartModel path) {
		this.path = path;
	}

	/* dijkstra section */
	@PostConstruct
	private void initDijkstraChart() {
		renderedPath = new HorizontalBarChartModel();
		ChartSeries cs = new ChartSeries();
		cs.set("Your path", 0);
		renderedPath.addSeries(cs);
		renderedPath.setStacked(true);
		renderedPath.setAnimate(true);
	}

	// rendering
	public void renderPath() {
		if (dijkstraStart == 0 || dijkstraDestination == 0) {
			dijkstraError();
			return;
		}
		startInfo = null;
		finishInfo = null;
		distanceInfo = 0;
		renderedPath.clear();
		GraphManager gt = new RandomDataGenerator();
		dijkstraPath = gt.getPath(this.dijkstraStart, this.dijkstraDestination);
		// displays a path on the chart
		int size = 0;
		for (int i = 0; i < dijkstraPath.pathLength; i++) {
			ChartSeries cs = new ChartSeries();
			cs.setLabel(Integer.toString(dijkstraPath.getId(i)));
			cs.set("Your path", dijkstraPath.getDistance(i));
			size += dijkstraPath.getDistance(i);
			renderedPath.addSeries(cs);
		}
		setDijkstraAxis(size);
	}

	// invoked if input data is empty
	public void dijkstraError() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Error!", "Empty data");
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	// refresh maximum xAxis after ChartSeries addition
	private void setDijkstraAxis(int size) {
		Axis xAxis = renderedPath.getAxis(AxisType.X);
		xAxis.setMin(0);
		xAxis.setMax(size + size / 14);
	}

	// info on click
	public void showInfo(ItemSelectEvent event) {
		int index = event.getSeriesIndex();
		if (index > 0)
			startInfo = renderedPath.getSeries().get(index - 1).getLabel();
		else
			startInfo = Integer.toString(selectedVertex.getId());
		finishInfo = renderedPath.getSeries().get(index).getLabel();
		distanceInfo = renderedPath.getSeries().get(index).getData()
				.get("Your path").intValue();
	}

	/* getters and setters */
	public int getDijkstraStart() {
		return dijkstraStart;
	}

	public void setDijkstraStart(int dijkstraStart) {
		this.dijkstraStart = dijkstraStart;
	}

	public int getDijkstraDestination() {
		return dijkstraDestination;
	}

	public void setDijkstraDestination(int dijkstraDestination) {
		this.dijkstraDestination = dijkstraDestination;
	}

	public HorizontalBarChartModel getRenderedPath() {
		return renderedPath;
	}

	public void setRenderedPath(HorizontalBarChartModel renderedPath) {
		this.renderedPath = renderedPath;
	}

	public String getstartInfo() {
		return startInfo;
	}

	public void setstartInfo(String startInfo) {
		this.startInfo = startInfo;
	}

	public String getFinishInfo() {
		return finishInfo;
	}

	public void setFinishInfo(String finish) {
		this.finishInfo = finish;
	}

	public double getDistanceInfo() {
		return distanceInfo;
	}

	public void setDistanceInfo(double range) {
		this.distanceInfo = range;
	}

	/* Vertex addition section */
	public int getVertexIdToAdd() {
		return vertexIdToAdd;
	}

	public void setVertexIdToAdd(int vertexIdToAdd) {
		this.vertexIdToAdd = vertexIdToAdd;
	}

	public double getVertexDelay() {
		return vertexDelayToAdd;
	}

	public void setVertexDelay(double vertexDelayToAdd) {
		this.vertexDelayToAdd = vertexDelayToAdd;
	}

	public void addVertex() {
		if (vertexExist(this.vertexIdToAdd) == false) {
			GraphManager gm = new RandomDataGenerator();
			gm.addVertex(vertexIdToAdd, vertexDelayToAdd);
			FacesContext.getCurrentInstance().addMessage(
					"addVertexPanel",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
							"Graph with Name '" + vertexIdToAdd
									+ "' and Delay =  " + vertexDelayToAdd
									+ " was added"));
			this.vertexIdToAdd = 0;
			this.vertexDelayToAdd = 0;
		} else
			FacesContext.getCurrentInstance().addMessage(
					"addVertexPanel",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed!",
							"Vertex with name '" + vertexIdToAdd
									+ "' is already exist"));

	}

	/* Vertex removal section */
	public int getVertexIdToDelete() {
		return vertexIdToDelete;
	}

	public void setVertexIdToDelete(int vertexIdToDelete) {
		this.vertexIdToDelete = vertexIdToDelete;
	}

	public void deleteVertex() {
		if (vertexExist(this.vertexIdToDelete) == true) {
			GraphManager gm = new RandomDataGenerator();
			gm.deleteVertex(vertexIdToDelete);
			String message = "vertex with name '" + vertexIdToDelete
					+ "' was removed";
			FacesContext.getCurrentInstance().addMessage(
					"deleteVertexPanel",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
							message));
			this.vertexIdToDelete = 0;
		} else
			FacesContext.getCurrentInstance().addMessage(
					"deleteVertexPanel",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed!",
							"Vertex with name '" + vertexIdToDelete
									+ "' doesn't exist"));
	}

	public boolean vertexExist(int vertexId) {
		GraphManager gm = new RandomDataGenerator();
		return gm.vertexExist(vertexId);
	}

	/* Edge addition section */
	public int getStartAdd() {
		return startAdd;
	}

	public void setStartAdd(int startAdd) {
		this.startAdd = startAdd;
	}

	public int getFinishAdd() {
		return finishAdd;
	}

	public void setFinishAdd(int finishAdd) {
		this.finishAdd = finishAdd;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public void addEdge() {
		GraphManager gm = new RandomDataGenerator();
		gm.addEdge(startAdd, finishAdd, range);
		// if this edge exists rewrite range?
		String message = "Edge from '" + startAdd + "' to '";
		message += finishAdd;
		message += "' was added";
		FacesContext.getCurrentInstance().addMessage(
				"addEdgePanel",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						message));
	}

	/* Edge removal section */
	public int getStartDelete() {
		return startDelete;
	}

	public void setStartDelete(int start) {
		this.startDelete = start;
	}

	public int getFinishDelete() {
		return finishDelete;
	}

	public void setFinishDelete(int finish) {
		this.finishDelete = finish;
	}

	public void deleteEdge() {
		GraphManager gm = new RandomDataGenerator();
		gm.deleteVertex(vertexIdToDelete);
		String message = "Edge from '" + startDelete + "' to '";
		message += finishDelete;
		message += "' was deleted";
		FacesContext.getCurrentInstance().addMessage(
				"deleteEdgePanel",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!",
						message));
	}

	public boolean edgeExist(int startId, int finishId) {
		GraphManager gm = new RandomDataGenerator();
		return gm.edgeExist(startId, finishId);
	}

	public String goHome() {
		return "Home";
	}
}
