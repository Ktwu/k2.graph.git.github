package edu.cmu.cs211.pg.game.GUI;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.Properties;
import edu.cmu.cs211.pg.game.Team;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.WeightedEdge;

import java.awt.event.KeyListener;

public class PGFrame extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	PGPanel main;
	JTable t;
	Graph<PirateNode,WeightedEdge<PirateNode>> graph;
	public PGFrame(Properties p, Team a, Team fightingMongooses, Graph<PirateNode,WeightedEdge<PirateNode>> graph, List<PirateNode> orderedVertices, int gold, int pyrite1, int pyrite2, int natives)
	{
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5,5));
		main = new PGPanel(p,a,fightingMongooses, graph, orderedVertices, gold, pyrite1, pyrite2, natives);
		main.setPreferredSize(main.dim);
		this.add(main, BorderLayout.CENTER);
		
		
		t = new JTable(new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			Object[] columnNames = new String[]{"","Student Information", "Robot Information"};
			Object[][] rowData = new Object[][]{{"Turn Number: ?", "Player blah (Blue)", "Player blah (Red)"},
												{"Cartographer Done?", "1", "1"},
												{"Current Node","PORT","PORT"},
												{"Destination Node", "NONE", "NONE"},
												{"Turns Left on Move", "0", "0"},
												{"Found gold?", "0","0"},
												{"Found pyrite?", "0", "0"}};
		    public String getColumnName(int col) {
		        return columnNames[col].toString();
		    }
		    public int getRowCount() { return rowData.length; }
		    public int getColumnCount() { return columnNames.length; }
		    public Object getValueAt(int row, int col) {
		        return rowData[row][col];
		    }
		    public boolean isCellEditable(int row, int col)
		        { return false; }
		    public void setValueAt(Object value, int row, int col) {
		        rowData[row][col] = value;
		        fireTableCellUpdated(row, col);
		    }
		});
		for (int i = 0; i < 3; i++) {
		    TableColumn column = t.getColumnModel().getColumn(i);
		    if (i == 0) {
		        column.setPreferredWidth(10); //first column is smaller
		    } else {
		        column.setPreferredWidth(300);
		    }
		}

		t.setVisible(true);
		this.add(t, BorderLayout.PAGE_END);
		
		pack();
		//setSize(main.getWidth() + t.getWidth(), main.getHeight() + t.getHeight());
		setVisible(true);
		this.graph = graph;
		t.addKeyListener(this);
		main.addKeyListener(this);
		this.addKeyListener(this);
	}
	public void setTurnNumber(int turn){
		t.setValueAt("Turn Number: " + turn, 0, 0);
	}
	public void setPlayer(int player, String name){
		t.setValueAt(name + " " + (player==0 ? "(Blue)": "(Red)"), 0, player+1);
	}
	public void setPhase(int player, int phase){
		t.setValueAt(phase, 1, player+1);
	}
	public void setLoc(int player, PirateNode p){
		main.setLoc(player, p);
		t.setValueAt(p.identity, 2, player+1);
	}
	public void setDest(int player, PirateNode p, WeightedEdge<PirateNode> edge){
		main.setEdge(player, edge);
		t.setValueAt(p.identity, 3, player+1);
	}
	public void setTurnsLeft(int player, int turns){
		main.setWait(player, turns);
		t.setValueAt(turns, 4, player+1);
	}
	public void setGold(int player, int howmuch){
		t.setValueAt(howmuch, 5, player+1);
	}
	public void setFoundPyrite(int player, int howmuch){
		t.setValueAt(howmuch, 6, player+1);
	}
	public void keyPressed(KeyEvent arg0) {
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
		switch(arg0.getKeyChar())
		{
			case 'e':
				main.flipEdges();
			break;
		}
	}
}
