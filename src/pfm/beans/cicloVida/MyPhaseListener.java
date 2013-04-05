package pfm.beans.cicloVida;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class MyPhaseListener implements PhaseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent pe) {
		System.out.println("after: " + pe.getPhaseId().toString());
		if (pe.getPhaseId() == PhaseId.RENDER_RESPONSE)
			System.out
					.println("-----------------------------------------------");
	}

	@Override
	public void beforePhase(PhaseEvent pe) {
		System.out.println("before: " + pe.getPhaseId().toString());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
