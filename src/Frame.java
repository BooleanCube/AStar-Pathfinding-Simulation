import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	ControlHandler ch;
	JFrame window;
	APathfinding pathfinding;
	boolean showSteps, btnHover;
	int size;
	double a1, a2;
	char currentKey = (char) 0;
	Node startNode, endNode;
	String mode;
	
	Timer timer = new Timer(100, this);
	int r = randomWithRange(0, 255);
	int G = randomWithRange(0, 255);
	int b = randomWithRange(0, 255);

	public static void main(String[] args) {
		new Frame();
	}

	public Frame() {
		ch = new ControlHandler(this);
		size = 25;
		mode = "Map Creation";
		showSteps = true;
		btnHover = false;
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pathfinding = new APathfinding(this, size);
		pathfinding.setDiagonal(true);
		a1 = (5000.0000 / (Math.pow(25.0000/5000, 1/49.0)));
		a2 = 625.0000;
		window = new JFrame();
		window.setContentPane(this);
		window.setTitle("A* Pathfinding Simulation");
		window.getContentPane().setPreferredSize(new Dimension(800, 700));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		ch.addAll();
		this.revalidate();
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = getHeight();
		int width = getWidth();
		if(pathfinding.isNoPath()) {
			timer.setDelay(50);
			timer.start();
			ch.getB("run").setText("reset");
			mode = "No Path";
			Color flicker = new Color(r, G, b);
			g.setColor(flicker);
			g.fillRect(0, 0, getWidth(), getHeight());
			ch.noPathTBounds();
			ch.getL("noPathT").setVisible(true);
			this.add(ch.getL("noPathT"));
			this.revalidate();
		}
		if(pathfinding.isComplete()) {
			ch.getB("run").setText("reset");
			timer.setDelay(50);
			timer.start();
			Color flicker = new Color(r, G, b);
			g.setColor(flicker);
			g.fillRect(0, 0, getWidth(), getHeight());
			if(showSteps) mode = "Completed";
			else mode = "Completed in " + pathfinding.getRunTime() + "ms";
		}
		g.setColor(Color.lightGray);
		for(int j = 0; j < this.getHeight(); j += size) {
			for(int i = 0; i < this.getWidth(); i += size) {
				g.drawRect(i, j, size, size);
			}
		}
		g.setColor(Color.black);
		for(int i = 0; i < pathfinding.getBorderList().size(); i++)
			g.fillRect(pathfinding.getBorderList().get(i).x + 1, pathfinding.getBorderList().get(i).y + 1, size - 1, size - 1);
		for(int i = 0; i < pathfinding.getOpenList().size(); i++) {
			Node current = pathfinding.getOpenList().get(i);
			g.setColor(style.greenHighlight);
			g.fillRect(current.x + 1, current.y + 1, size - 1, size - 1);
			drawInfo(current, g);
		}
		for(int i = 0; i < pathfinding.getClosedList().size(); i++) {
			Node current = pathfinding.getClosedList().get(i);
			g.setColor(style.redHighlight);
			g.fillRect(current.x + 1, current.y + 1, size - 1, size - 1);
			drawInfo(current, g);
		}
		for(int i = 0; i < pathfinding.getPathList().size(); i++) {
			Node current = pathfinding.getPathList().get(i);
			g.setColor(style.blueHighlight);
			g.fillRect(current.x + 1, current.y + 1, size - 1, size - 1);
			drawInfo(current, g);
		}
		if(startNode != null) {
			g.setColor(Color.blue);
			g.fillRect(startNode.x + 1, startNode.y + 1, size - 1, size - 1);
		}
		if(endNode != null) {
			g.setColor(Color.red);
			g.fillRect(endNode.x + 1, endNode.y + 1, size - 1, size - 1);
		}
		if(btnHover) {
			g.setColor(style.darkText);
			ch.hoverColor();
		} else {
			g.setColor(style.btnPanel);
			ch.nonHoverColor();
		}
		g.fillRect(10, height-96, 322, 90);
		ch.getL("modeText").setText("Mode: " + mode);
		ch.position();
		ch.getL("openC").setText(Integer.toString(pathfinding.getOpenList().size()));
		ch.getL("closedC").setText(Integer.toString(pathfinding.getClosedList().size()));
		ch.getL("pathC").setText(Integer.toString(pathfinding.getPathList().size()));
		if(showSteps) ch.getL("speedC").setText(Integer.toString(ch.getS("speed").getValue()));
		else ch.getL("speedC").setText("N/A");
		showSteps = ch.getC("showStepsCheck").isSelected();
		pathfinding.setDiagonal(ch.getC("diagonalCheck").isSelected());
	}

	public void drawInfo(Node current, Graphics g) {
		if(size > 50) {
			g.setFont(style.numbers);
			g.setColor(Color.black);
			g.drawString(Integer.toString(current.f), current.x + 4, current.y + 16);
			g.setFont(style.smallNumbers);
			g.drawString(Integer.toString(current.g), current.x + 4, current.y + size - 7);
			g.drawString(Integer.toString(current.h), current.x + size - 26, current.y + size - 7);
		}
	}

	public void MapCalculations(MouseEvent e) {
		if(!mode.equalsIgnoreCase("Map Creation")) return;

		if(SwingUtilities.isLeftMouseButton(e)) {
			if(currentKey == 's') {
				int x = e.getX() % size;
				int y = e.getY() % size;
				if(startNode == null) startNode = new Node(e.getX() - x, e.getY() - y);
				else startNode.setXY(e.getX() - x, e.getY() - y);
			}
			else if(currentKey == 'e') {
				int x = e.getX() - (e.getX() % size);
				int y = e.getY() - (e.getY() % size);
				int location = pathfinding.searchBorder(x, y);
				if(location != -1) pathfinding.removeBorder(location);
				if(endNode == null) endNode = new Node(x, y);
				else endNode.setXY(x, y);
			}
			else {
				int x = e.getX() - (e.getX() % size);
				int y = e.getY() - (e.getY() % size);
				pathfinding.addBorder(new Node(x, y));
			}
			repaint();
		}
		else if(SwingUtilities.isRightMouseButton(e)) {
			int x = e.getX() - (e.getX() % size);
			int y = e.getY() - (e.getY() % size);
			if(currentKey == 's' && (startNode != null && x == startNode.x && startNode.y == y)) startNode = null;
			else if(currentKey == 'e' && (endNode != null && x == endNode.x && endNode.y == y)) endNode = null;
			else {
				int location = pathfinding.searchBorder(x, y);
				if(location > -1) pathfinding.removeBorder(location);
			}
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MapCalculations(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		MapCalculations(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y =e.getY();
		int height = this.getHeight();
		btnHover = x >= 10 && x <= 332 && y >= (height - 96) && y <= (height - 6);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		currentKey = e.getKeyChar();
		if(currentKey == KeyEvent.VK_SPACE) {
			ch.getB("run").setText("stop");
			start();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { currentKey = (char) 0; }
	boolean start() {
		if(startNode != null && endNode != null) {
			if(!showSteps) pathfinding.start(startNode, endNode);
			else {
				pathfinding.setup(startNode, endNode);
				setSpeed();
				timer.start();
			}
		}
		return false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent m) {
		if(startNode != null || endNode != null || !pathfinding.getBorderList().isEmpty()) return;

		int rotation = m.getWheelRotation();
		double prevSize = size;
		int scroll = 3;

		if(rotation == -1 && size + scroll < 80) {
			size += scroll;
		} else if(rotation == 1 && size - scroll > 20) {
			size += -scroll;
		}
		pathfinding.setSize(size);
		double ratio = size / prevSize;

		if(startNode != null) {
			int sX = (int) Math.round(startNode.x * ratio);
			int sY = (int) Math.round(startNode.y * ratio);
			startNode.setXY(sX, sY);
		}

		if(endNode != null) {
			int eX = (int) Math.round(endNode.x * ratio);
			int eY = (int) Math.round(endNode.y * ratio);
			endNode.setXY(eX, eY);
		}

		for(int i = 0; i < pathfinding.getBorderList().size(); i++) {
			int newX = (int) Math.round((pathfinding.getBorderList().get(i).x * ratio));
			int newY = (int) Math.round((pathfinding.getBorderList().get(i).y * ratio));
			pathfinding.getBorderList().get(i).setXY(newX, newY);
		}

		for(int i = 0; i < pathfinding.getOpenList().size(); i++) {
			int newX = (int) Math.round((pathfinding.getOpenList().get(i).x * ratio));
			int newY = (int) Math.round((pathfinding.getOpenList().get(i).y * ratio));
			pathfinding.getOpenList().get(i).setXY(newX, newY);
		}

		for(int i = 0; i < pathfinding.getClosedList().size(); i++) {
			if(!pathfinding.getClosedList().get(i).isEqual(startNode)) {
				int newX = (int) Math.round((pathfinding.getClosedList().get(i).x * ratio));
				int newY = (int) Math.round((pathfinding.getClosedList().get(i).y * ratio));
				pathfinding.getClosedList().get(i).setXY(newX, newY);
			}
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(pathfinding.isRunning() && showSteps) {
			pathfinding.findPath(pathfinding.getPar());
			mode = "Running";
		}
		if(pathfinding.isComplete() || pathfinding.isNoPath()) {
			r = (int) (Math.random() * ((r + 15) - (r - 15)) + (r - 15));
			G = (int) (Math.random() * ((G + 15) - (G - 15)) + (G - 15));
			b = (int) (Math.random() * ((b + 15) - (b - 15)) + (b - 15));
			if(r >= 240 | r <= 15) r = randomWithRange(0, 255);
			if(G >= 240 | G <= 15) G = randomWithRange(0, 255);
			if(b >= 240 | b <= 15) b = randomWithRange(0, 255);
		}
		if(e.getActionCommand() != null) {
			if(e.getActionCommand().equals("run") && !pathfinding.isRunning()) {
				if(!start()) return;
				ch.getB("run").setText("pause");
			} else if(e.getActionCommand().equals("reset")) {
				ch.getB("run").setText("run");
				mode = "Map Creation";
				ch.getL("noPathT").setVisible(false);
				pathfinding.reset();
			} else if(e.getActionCommand().equals("pause")) {
				ch.getB("run").setText("play");
				timer.stop();
			} else if(e.getActionCommand().equals("play")) {
				ch.getB("run").setText("pause");
				if(!showSteps) { pathfinding.start(startNode, endNode); return; }
				timer.start();
			} else if(e.getActionCommand().equals("clear")) {
				if(mode.equalsIgnoreCase("Map Creation")) {
					startNode = null; endNode = null;
					pathfinding.getBorderList().clear();
				}
			}
		}
		repaint();
	}

	int randomWithRange(int min, int max) {
	   int range = (max - min) + 1;
	   return (int)(Math.random() * range) + min;
	}

	void setSpeed() {
		int delay = 0;
		int value = ch.getS("speed").getValue();
		if(value == 0) timer.stop();
		else if(value >= 1 && value < 50) {
			if(!timer.isRunning()) timer.start();
			delay = (int)(a1 * (Math.pow(25/5000.0000, value / 49.0000)));
		}
		else if(value >= 50 && value <= 100) {
			if(!timer.isRunning()) timer.start();
			delay = (int)(a2 * (Math.pow(1/25.0000, value/50.0000)));
		}
		timer.setDelay(delay);
	}

	boolean showSteps() { return showSteps; }

}
