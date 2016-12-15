package com.peraglobal.crawler.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.peraglobal.common.IDGenerate;
import com.peraglobal.crawler.process.MetaDataBuilder.FileDataField;
import com.peraglobal.crawler.process.MetaDataBuilder.MetaDataWrapper;
import com.peraglobal.crawler.util.Dom4jXmlUtil;
import com.peraglobal.mongo.service.DatumService;
import com.peraglobal.mongo.model.Datum;
import com.peraglobal.db.model.Attachment;
import com.peraglobal.db.service.AttachmentService;

@Scope("prototype")
@Component("metaDataDBHandler")
public class MetaDataDBHandler {

	@Resource
	private DatumService datumService;
	
	@Resource
	private AttachmentService attachmentService;

	private boolean isAdd = false;
	private boolean isUpdated = false;
	private AtomicLong failCount = new AtomicLong(0);
	private AtomicLong successCount = new AtomicLong(0);
	

	// 修改成功数
	private void monitor(MetaDataWrapper metaData) {
		// 后续完善
	}

	protected void onSuccess() {
		successCount.incrementAndGet();
	}

	protected void onError() {
		failCount.incrementAndGet();
	}

	@SuppressWarnings("static-access")
	public void storage(final MetaDataWrapper metaData) {
		try {
			Query query = new Query();
			query.addCriteria(new Criteria("dbpk").is(metaData.getPk()).and("taskId").is(metaData.getTaskId()));
			List<Datum> kmDatumList = this.datumService.findByCondition(query);
			Datum d = null;
			if (kmDatumList != null && kmDatumList.size() > 0) {
				d = kmDatumList.get(0);
			}
			if (null == d) {// 创建
				String uuid = IDGenerate.uuid();
				Attachment a = null;
				List<FileDataField> rowFileDataFields = metaData.getRowFileDataFields();
				List<String> attachmentIds = new ArrayList<String>();
				for (FileDataField fileDataField : rowFileDataFields) {
					a = new Attachment();
					a.setDatumId(uuid);
					String fileType = fileDataField.getFileTypeValue();
					if (null != fileType && fileType.indexOf(MetaDataWrapper.FILETPYEDOT) == -1) {
						fileType = MetaDataWrapper.FILETPYEDOT + fileType;
					}
					a.setPath(fileDataField.getFileNameValue());
					a.setName(fileDataField.getName() + "." + fileDataField.getFileTypeValue());
					a.setCrawlerId(metaData.getTaskId());
					a.setType("." + fileDataField.getFileTypeValue());
				}
				d = new Datum();
				d.setId(uuid);
				d.setTaskId(metaData.getTaskId());
				d.setKvs(metaData.getObjKvs());
				d.setIsFull(Dom4jXmlUtil.checkIsFull(metaData.getObjKvs()));
				d.setDbpk(metaData.getPk());
				d.setMd5(metaData.getMd5());
				d.setCreateDate(new Date());
				datumService.insert(d);
				if (a != null) {
					attachmentService.save(a);
					attachmentIds.add(a.getAttachmentId());
				}
				onSuccess();
				isAdd = true;
			} else if (metaData.getTaskId().equals(d.getTaskId()) && !metaData.getMd5().equals(d.getMd5())
					&& metaData.getPk().equals(d.getDbpk())) {// 更新

				List<String> attachmentIds = new ArrayList<String>();
				List<FileDataField> rowFileDataFields = metaData.getRowFileDataFields();
				for (FileDataField fileDataField : rowFileDataFields) {
					// 更新文件
					String fileType = fileDataField.getFileTypeValue();
					if (null != fileType && fileType.indexOf(metaData.FILETPYEDOT) == -1) {
						fileType = metaData.FILETPYEDOT + fileType;
					}

					// 文件数据入库
					for (String attachmentId : d.getAttachmentIds()) {
						Attachment a = attachmentService.findById(attachmentId);
						if (null == a) {
							a = new Attachment();
							a.setAttachmentId(IDGenerate.uuid());
							a.setDatumId(d.getId());
							fileType = fileDataField.getFileTypeValue();
							if (null != fileType && fileType.indexOf(MetaDataWrapper.FILETPYEDOT) == -1) {
								fileType = MetaDataWrapper.FILETPYEDOT + fileType;
							}
							a.setPath(metaData.getDataFilePath() + fileDataField.getFileNameValue() + fileType);
							a.setName(fileDataField.getFileNameValue());
							a.setCrawlerId(metaData.getTaskId());
							a.setType(fileType);
							attachmentService.save(a);
							attachmentIds.add(a.getAttachmentId());
						} else {
							fileType = fileDataField.getFileTypeValue();
							if (null != fileType && fileType.indexOf(MetaDataWrapper.FILETPYEDOT) == -1) {
								fileType = MetaDataWrapper.FILETPYEDOT + fileType;
							}
							a.setPath(metaData.getDataFilePath() + fileDataField.getFileNameValue() + fileType);
							a.setName(fileDataField.getFileNameValue());
							a.setType(fileType);
							attachmentService.updateIncrement(a);
						}

					}
				}
				d.setKvs(metaData.getObjKvs()); // 创建元数据xml
				d.setIsFull(Dom4jXmlUtil.checkIsFull(metaData.getObjKvs()));
				d.setMd5(metaData.getMd5());
				// 更新
				if (null != attachmentIds && attachmentIds.size() != 0) {
					String[] oldAttachIds = d.getAttachmentIds();
					String[] newAttachIds = attachmentIds.toArray(new String[attachmentIds.size()]);
					d.setAttachmentIds(getMergeArray(oldAttachIds, newAttachIds));
				}
				this.datumService.save(d);
				onSuccess();
				isAdd = true;
			}
			if (isAdd && !isUpdated) {
				// 更新采集任务的转换任务(状态为停止)：有新数据提醒
				// this.taskJobBiz.updateJobHaveNewData(((Datum)
				// d).getTaskId());
				isUpdated = true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			onError();
		} finally {
			monitor(metaData);
		}
	}

	public String[] getMergeArray(String[] al, String[] bl) {
		String[] a = al;
		String[] b = bl;
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
}
