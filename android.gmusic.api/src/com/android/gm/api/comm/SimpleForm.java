package com.android.gm.api.comm;

import java.io.IOException;
import java.util.Map;

public final class SimpleForm
{
	private StringBuilder mForm = new StringBuilder();

	private static final String TWO_HYPHENS = "--";
	private static final String CRLF = "\r\n";

	private final String mBoundary = "**** " + System.currentTimeMillis() + " ****";

	private final String mContentType = "multipart/form-data; boundary=" + mBoundary;

	private boolean isClosed;

	// returns itself to support chaining
	public final SimpleForm addFields(Map<String, String> fields) throws IOException
	{
		for(String key : fields.keySet())
		{
			addField(key, fields.get(key));
		}

		return this;
	}

	// returns itself to support chaining
	public final SimpleForm addField(String key, String value)
	{
		if(!isClosed)
		{
			mForm.append(CRLF + TWO_HYPHENS + mBoundary + CRLF);
			mForm.append("Content-Disposition: form-data; name=\"");
			mForm.append(key);
			mForm.append("\"" + CRLF + CRLF);
			mForm.append(value);
		}
		else
			throw new IllegalArgumentException("You can not add fields after close has been called.");
		return this;
	}

	// returns itself to support chaining
	public final SimpleForm close()
	{
		if(!isClosed)
		{
			mForm.append(CRLF + TWO_HYPHENS + mBoundary + TWO_HYPHENS + CRLF);
			isClosed = true;
		}
		return this;
	}

	public final String getContentType()
	{
		return mContentType;
	}

	@Override
	public String toString()
	{
		return mForm.toString();
	}
}
