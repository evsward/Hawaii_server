// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WptMsg.proto

package com.evsward.server.protobuf;

public final class WptMessage {
  private WptMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface WptReqMessageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required string jsonReqMsg = 1;
    boolean hasJsonReqMsg();
    String getJsonReqMsg();
  }
  public static final class WptReqMessage extends
      com.google.protobuf.GeneratedMessage
      implements WptReqMessageOrBuilder {
    // Use WptReqMessage.newBuilder() to construct.
    private WptReqMessage(Builder builder) {
      super(builder);
    }
    private WptReqMessage(boolean noInit) {}
    
    private static final WptReqMessage defaultInstance;
    public static WptReqMessage getDefaultInstance() {
      return defaultInstance;
    }
    
    public WptReqMessage getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.evsward.server.protobuf.WptMessage.internal_static_WptReqMessage_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.evsward.server.protobuf.WptMessage.internal_static_WptReqMessage_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required string jsonReqMsg = 1;
    public static final int JSONREQMSG_FIELD_NUMBER = 1;
    private java.lang.Object jsonReqMsg_;
    public boolean hasJsonReqMsg() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public String getJsonReqMsg() {
      java.lang.Object ref = jsonReqMsg_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          jsonReqMsg_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getJsonReqMsgBytes() {
      java.lang.Object ref = jsonReqMsg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        jsonReqMsg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      jsonReqMsg_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasJsonReqMsg()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getJsonReqMsgBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getJsonReqMsgBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptReqMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.evsward.server.protobuf.WptMessage.WptReqMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.evsward.server.protobuf.WptMessage.WptReqMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.evsward.server.protobuf.WptMessage.internal_static_WptReqMessage_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.evsward.server.protobuf.WptMessage.internal_static_WptReqMessage_fieldAccessorTable;
      }
      
      // Construct using com.evsward.server.protobuf.WptMessage.WptReqMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        jsonReqMsg_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.evsward.server.protobuf.WptMessage.WptReqMessage.getDescriptor();
      }
      
      public com.evsward.server.protobuf.WptMessage.WptReqMessage getDefaultInstanceForType() {
        return com.evsward.server.protobuf.WptMessage.WptReqMessage.getDefaultInstance();
      }
      
      public com.evsward.server.protobuf.WptMessage.WptReqMessage build() {
        com.evsward.server.protobuf.WptMessage.WptReqMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.evsward.server.protobuf.WptMessage.WptReqMessage buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.evsward.server.protobuf.WptMessage.WptReqMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.evsward.server.protobuf.WptMessage.WptReqMessage buildPartial() {
        com.evsward.server.protobuf.WptMessage.WptReqMessage result = new com.evsward.server.protobuf.WptMessage.WptReqMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.jsonReqMsg_ = jsonReqMsg_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.evsward.server.protobuf.WptMessage.WptReqMessage) {
          return mergeFrom((com.evsward.server.protobuf.WptMessage.WptReqMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.evsward.server.protobuf.WptMessage.WptReqMessage other) {
        if (other == com.evsward.server.protobuf.WptMessage.WptReqMessage.getDefaultInstance()) return this;
        if (other.hasJsonReqMsg()) {
          setJsonReqMsg(other.getJsonReqMsg());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasJsonReqMsg()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              jsonReqMsg_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required string jsonReqMsg = 1;
      private java.lang.Object jsonReqMsg_ = "";
      public boolean hasJsonReqMsg() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getJsonReqMsg() {
        java.lang.Object ref = jsonReqMsg_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          jsonReqMsg_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setJsonReqMsg(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        jsonReqMsg_ = value;
        onChanged();
        return this;
      }
      public Builder clearJsonReqMsg() {
        bitField0_ = (bitField0_ & ~0x00000001);
        jsonReqMsg_ = getDefaultInstance().getJsonReqMsg();
        onChanged();
        return this;
      }
      void setJsonReqMsg(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000001;
        jsonReqMsg_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:WptReqMessage)
    }
    
    static {
      defaultInstance = new WptReqMessage(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:WptReqMessage)
  }
  
  public interface WptAckMessageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required string jsonAckMsg = 1;
    boolean hasJsonAckMsg();
    String getJsonAckMsg();
  }
  public static final class WptAckMessage extends
      com.google.protobuf.GeneratedMessage
      implements WptAckMessageOrBuilder {
    // Use WptAckMessage.newBuilder() to construct.
    private WptAckMessage(Builder builder) {
      super(builder);
    }
    private WptAckMessage(boolean noInit) {}
    
    private static final WptAckMessage defaultInstance;
    public static WptAckMessage getDefaultInstance() {
      return defaultInstance;
    }
    
    public WptAckMessage getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.evsward.server.protobuf.WptMessage.internal_static_WptAckMessage_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.evsward.server.protobuf.WptMessage.internal_static_WptAckMessage_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required string jsonAckMsg = 1;
    public static final int JSONACKMSG_FIELD_NUMBER = 1;
    private java.lang.Object jsonAckMsg_;
    public boolean hasJsonAckMsg() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public String getJsonAckMsg() {
      java.lang.Object ref = jsonAckMsg_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          jsonAckMsg_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getJsonAckMsgBytes() {
      java.lang.Object ref = jsonAckMsg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        jsonAckMsg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      jsonAckMsg_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasJsonAckMsg()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getJsonAckMsgBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getJsonAckMsgBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.evsward.server.protobuf.WptMessage.WptAckMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.evsward.server.protobuf.WptMessage.WptAckMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.evsward.server.protobuf.WptMessage.WptAckMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.evsward.server.protobuf.WptMessage.internal_static_WptAckMessage_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.evsward.server.protobuf.WptMessage.internal_static_WptAckMessage_fieldAccessorTable;
      }
      
      // Construct using com.evsward.server.protobuf.WptMessage.WptAckMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        jsonAckMsg_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.evsward.server.protobuf.WptMessage.WptAckMessage.getDescriptor();
      }
      
      public com.evsward.server.protobuf.WptMessage.WptAckMessage getDefaultInstanceForType() {
        return com.evsward.server.protobuf.WptMessage.WptAckMessage.getDefaultInstance();
      }
      
      public com.evsward.server.protobuf.WptMessage.WptAckMessage build() {
        com.evsward.server.protobuf.WptMessage.WptAckMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.evsward.server.protobuf.WptMessage.WptAckMessage buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.evsward.server.protobuf.WptMessage.WptAckMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.evsward.server.protobuf.WptMessage.WptAckMessage buildPartial() {
        com.evsward.server.protobuf.WptMessage.WptAckMessage result = new com.evsward.server.protobuf.WptMessage.WptAckMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.jsonAckMsg_ = jsonAckMsg_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.evsward.server.protobuf.WptMessage.WptAckMessage) {
          return mergeFrom((com.evsward.server.protobuf.WptMessage.WptAckMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.evsward.server.protobuf.WptMessage.WptAckMessage other) {
        if (other == com.evsward.server.protobuf.WptMessage.WptAckMessage.getDefaultInstance()) return this;
        if (other.hasJsonAckMsg()) {
          setJsonAckMsg(other.getJsonAckMsg());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasJsonAckMsg()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              jsonAckMsg_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required string jsonAckMsg = 1;
      private java.lang.Object jsonAckMsg_ = "";
      public boolean hasJsonAckMsg() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getJsonAckMsg() {
        java.lang.Object ref = jsonAckMsg_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          jsonAckMsg_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setJsonAckMsg(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        jsonAckMsg_ = value;
        onChanged();
        return this;
      }
      public Builder clearJsonAckMsg() {
        bitField0_ = (bitField0_ & ~0x00000001);
        jsonAckMsg_ = getDefaultInstance().getJsonAckMsg();
        onChanged();
        return this;
      }
      void setJsonAckMsg(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000001;
        jsonAckMsg_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:WptAckMessage)
    }
    
    static {
      defaultInstance = new WptAckMessage(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:WptAckMessage)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_WptReqMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_WptReqMessage_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_WptAckMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_WptAckMessage_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014WptMsg.proto\"#\n\rWptReqMessage\022\022\n\njsonR" +
      "eqMsg\030\001 \002(\t\"#\n\rWptAckMessage\022\022\n\njsonAckM" +
      "sg\030\001 \002(\tB(\n\032com.evsward.server.protobufB\n" +
      "WptMessage"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_WptReqMessage_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_WptReqMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_WptReqMessage_descriptor,
              new java.lang.String[] { "JsonReqMsg", },
              com.evsward.server.protobuf.WptMessage.WptReqMessage.class,
              com.evsward.server.protobuf.WptMessage.WptReqMessage.Builder.class);
          internal_static_WptAckMessage_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_WptAckMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_WptAckMessage_descriptor,
              new java.lang.String[] { "JsonAckMsg", },
              com.evsward.server.protobuf.WptMessage.WptAckMessage.class,
              com.evsward.server.protobuf.WptMessage.WptAckMessage.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
