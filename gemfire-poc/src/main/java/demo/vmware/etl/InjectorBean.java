package demo.vmware.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.data.gemfire.GemfireTemplate;

import demo.vmware.domain.disney.Resort;

public class InjectorBean
{

	GemfireTemplate gtResort;

	String dataFile;

	Boolean loadData;

	FlatFileItemReader<Resort> resortItemReader;

	private static final Logger logger = LoggerFactory
			.getLogger(InjectorBean.class);

	public void loadData()
		throws Exception
	{
		if (loadData)
		{
			long start = System.currentTimeMillis();
			loadResorts();

			long stop = System.currentTimeMillis();

			logger.info("Data load complete in: " + (stop - start) + " ms.");
		}
	}

	private void loadResorts()
		throws Exception
	{
		resortItemReader.open(new ExecutionContext());
		Resort resort = resortItemReader.read();

		while (resort != null)
		{
			gtResort.put(resort.getId(), resort);
			resort = resortItemReader.read();
		}
	}

	public Boolean getLoadData()
	{
		return loadData;
	}

	public void setLoadData(Boolean loadData)
	{
		this.loadData = loadData;
	}

	public FlatFileItemReader<Resort> getResortItemReader()
	{
		return resortItemReader;
	}

	public void setResortItemReader(
			FlatFileItemReader<Resort> resortItemReader)
	{
		this.resortItemReader = resortItemReader;
	}

	public GemfireTemplate getGtResort()
	{
		return gtResort;
	}

	public void setGtResort(GemfireTemplate gtResort)
	{
		this.gtResort = gtResort;
	}

}