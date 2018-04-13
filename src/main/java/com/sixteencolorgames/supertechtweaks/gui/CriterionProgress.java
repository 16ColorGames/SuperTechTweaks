package com.sixteencolorgames.supertechtweaks.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.network.PacketBuffer;

public class CriterionProgress {
	private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	private final ResearchProgress researchProgress;
	private Date obtained;

	public CriterionProgress(ResearchProgress researchProgressIn) {
		this.researchProgress = researchProgressIn;
	}

	public boolean isObtained() {
		return this.obtained != null;
	}

	public void obtain() {
		this.obtained = new Date();
	}

	public void reset() {
		this.obtained = null;
	}

	public Date getObtained() {
		return this.obtained;
	}

	public String toString() {
		return "CriterionProgress{obtained=" + (this.obtained == null ? "false" : this.obtained) + '}';
	}

	public void write(PacketBuffer buf) {
		buf.writeBoolean(this.obtained != null);

		if (this.obtained != null) {
			buf.writeTime(this.obtained);
		}
	}

	public JsonElement serialize() {
		return (JsonElement) (this.obtained != null ? new JsonPrimitive(DATE_TIME_FORMATTER.format(this.obtained))
				: JsonNull.INSTANCE);
	}

	public static CriterionProgress read(PacketBuffer buf, ResearchProgress researchProgressIn) {
		CriterionProgress criterionprogress = new CriterionProgress(researchProgressIn);

		if (buf.readBoolean()) {
			criterionprogress.obtained = buf.readTime();
		}

		return criterionprogress;
	}

	public static CriterionProgress fromDateTime(ResearchProgress researchProgressIn, String dateTime) {
		CriterionProgress criterionprogress = new CriterionProgress(researchProgressIn);

		try {
			criterionprogress.obtained = DATE_TIME_FORMATTER.parse(dateTime);
			return criterionprogress;
		} catch (ParseException parseexception) {
			throw new JsonSyntaxException("Invalid datetime: " + dateTime, parseexception);
		}
	}
}