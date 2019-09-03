package Game.Entities.Statics;
import java.util.Collection;
import Game.Entities.EntityBase;
import Main.Handler;

/**
 * Created by Elemental on 1/1/2017.
 */
public abstract class StaticEntity extends EntityBase {

	

	public StaticEntity(Handler handler, float x, float y, int height, int width) {
		super(handler, x, y, height, width);
	}

	protected Boolean checkIfCountIsZero(Collection<Integer> values) {
		for(Integer val: values) {
			if(val !=0) {
				return false;
			}
		}
		return true;
	}
}

