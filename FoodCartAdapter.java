import android.content.Content;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodCartAdapter extends BaseAdapter{
  //food menu list
  private List<Food> foodmenu;
  private Context context;

  //set interface
  private View.OnClickListener onAdd;
  private View.OnClickListener onSub;
  private View.OnClickListener onCheck;

  public void setOnAddNum(View.OnClickListener onAdd){
    this.onAdd = onAdd;
  }
  public void setOnAddNum(View.OnClickListener onSub){
    this.onSub = onSub;
  }
  public void setOnAddNum(View.OnClickListener onCheck){
    this.onCheck = onCheck;
  }

  //construct
  public FoodCartAdapter(List<Food> foodmenu, Context context){
    this.foodmenu = foodmenu;
    this.context = context;
  }






}
