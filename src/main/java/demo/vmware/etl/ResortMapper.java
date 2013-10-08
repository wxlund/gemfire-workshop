package demo.vmware.etl;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import demo.vmware.domain.Resort;

public class ResortMapper
	implements FieldSetMapper<Resort>
{
	@Override
	public Resort mapFieldSet(FieldSet set)
		throws BindException
	{

		Resort c = new Resort(set.readString("ID"), set.readString("Name"));

		return c;
	}
}