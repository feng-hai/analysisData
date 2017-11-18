package com.newnetcom.anlyze.beans;

public class RuleBean {
	//�ֶ�����
	private String title;
	//�ֶα�ʶ
	private String id;
	//�ֶα���
	private String alias;
	//��ʼλ��  �ڼ�λ
	private int start;
	//����
	private int length;
	//ƫ����
	private int offset;
	//�ֱ���
	private int resolving;
	//�������ͣ�������ֵת��
	private String dataType;
	//��Ӧ��ֵ
	private String value;
	//�Ƿ��Ǹ����ֶΣ������ֶ�ֻ���ڼ��㣬��չʾ
	private Boolean isDesplay;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getResolving() {
		return resolving;
	}
	public void setResolving(int resolving) {
		this.resolving = resolving;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Boolean getIsDesplay() {
		return isDesplay;
	}
	public void setIsDesplay(Boolean isDesplay) {
		this.isDesplay = isDesplay;
	}
}
