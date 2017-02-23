package kevin.test.bluetooth.bluetooth_frame;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * @author KI
 * @version 1.0b
 */
public class DiagramFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String INSTANCESTATE_DIAGRAM_NAME = "Diagram name";
    private static final String INSTANCESTATE_DIAGRAM_HEIGHT = "Diagram height";
    private static final String INSTANCESTATE_DIAGRAM_WIDTH = "Diagram width";
    private static final String INSTANCESTATE_DIAGRAM_VALUES = "Diagram values";
    private static final String INSTANCESTATE_DIAGRAM_MINVALUE = "Diagram minimum value";
    private static final String INSTANCESTATE_DIAGRAM_MAXVALUE = "Diagram maximum value";
    private static final String INSTANCESTATE_DIAGRAM_UNIT = "Diagram unit";
    //Default values
    private static final int DEFAULT_COLOR_NAMETEXT = Color.BLACK;
    private static final int DEFAULT_COLOR_DIAGRAM_BACKGROUND = Color.WHITE;


    private String name, unit;
    private TextView nameView;
    private LinearLayout rootView;
    private Context attachContext;
    private Diagramm diagram;
    private ArrayList<Integer> values;
    private int height, width, min, max;


    public DiagramFragment() {
        //Android developer guides say you should leave an empty constructor and set everything necessary in an static factory
    }

    /**
     *
     * @param name the Name to be shown
     * @param diagram the Diagram to show information
     * @return a newly created DiagramFragment
     */
    /**
     * public static DiagramFragment newInstance(String name, Diagramm diagram) {
     * DiagramFragment fragment = new DiagramFragment();
     * Bundle args = new Bundle();
     * args.putString(INSTANCESTATE_DIAGRAM_NAME, name);
     * fragment.setArguments(args);
     * fragment.setDiagram(diagram);
     * return fragment;
     * }
     **/

    public static DiagramFragment newInstance(String name, int height, int width, ArrayList<Integer> values, int min, int max, String unit) {
        DiagramFragment fragment = new DiagramFragment();
        Bundle args = new Bundle();
        args.putString(INSTANCESTATE_DIAGRAM_NAME, name);
        args.putInt(INSTANCESTATE_DIAGRAM_HEIGHT, height);
        args.putInt(INSTANCESTATE_DIAGRAM_WIDTH, width);
        args.putIntegerArrayList(INSTANCESTATE_DIAGRAM_VALUES, values);
        args.putInt(INSTANCESTATE_DIAGRAM_MINVALUE, min);
        args.putInt(INSTANCESTATE_DIAGRAM_MAXVALUE, max);
        args.putString(INSTANCESTATE_DIAGRAM_UNIT, unit);
        fragment.setArguments(args);
        fragment.setDiagram(null);
        return fragment;
    }

    private void setDiagram(Diagramm diagram) {
        this.diagram = diagram;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            name = args.getString(INSTANCESTATE_DIAGRAM_NAME);
            height = args.getInt(INSTANCESTATE_DIAGRAM_HEIGHT);
            width = args.getInt(INSTANCESTATE_DIAGRAM_WIDTH);
            min = args.getInt(INSTANCESTATE_DIAGRAM_MINVALUE);
            max = args.getInt(INSTANCESTATE_DIAGRAM_MAXVALUE);
            values = args.getIntegerArrayList(INSTANCESTATE_DIAGRAM_VALUES);
            unit = args.getString(INSTANCESTATE_DIAGRAM_UNIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = new LinearLayout(container.getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setId(View.NO_ID);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(params);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachContext = context;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { //Activity view has been created, diagram may be added
        super.onActivityCreated(savedInstanceState);
        createLayout();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        attachContext = null;
        setDiagram(null);
        nameView = null;
        rootView = null;
        values.clear();
        values = null;
    }

    public void addToDiagram(Integer valueToAdd) {
        values.add(valueToAdd);
    }

    public void updateDiagram() {
        rootView.removeView(nameView);
        rootView.removeView(diagram);
        createLayout();
    }

    private void createLayout() {
        drawTextName();
        drawDiagram();
    }

    private void drawDiagram() {
        setDiagram(new Diagramm(attachContext, height, width, values, min, max, unit));
        diagram.setBackgroundColor(DEFAULT_COLOR_DIAGRAM_BACKGROUND);
        diagram.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        this.rootView.addView(diagram);
    }

    private void drawTextName() {
        this.nameView = new TextView(attachContext);
        nameView.setText(name);
        nameView.setTextColor(DEFAULT_COLOR_NAMETEXT);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameView.setLayoutParams(params);
        this.rootView.addView(nameView);
    }

}