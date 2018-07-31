package ai.couture.ra.core;

/**
 * The following class contains five attributes namely ModelId, Name, Version, HDFS_Path, Type
 * It is used to store a model as an object
 */

public class Model {

    private Integer ModelId;
    private String Name;
    private String Version;
    private String HDFS_Path;
    private String Type;

    /**
     * This method is used to set the ModelId of the object Model
     * @param modelId the modelId to be set
     */
    public void setModelId(Integer modelId) {
        ModelId = modelId;
    }

    /**
     * This method is used to set the Name of the object Model
     * @param name the name to be set
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * This method is used to set the Version of the object Model
     * @param version the version to be set
     */
    public void setVersion(String version) {
        Version = version;
    }

    /**
     * This method is used to set the HDFS_Path of the object Model
     * @param HDFS_Path the HDFS Path to be set
     */
    public void setHDFS_Path(String HDFS_Path) {
        this.HDFS_Path = HDFS_Path;
    }

    /**
     * This method is used to set the Type of the object Model
     * @param type the type to be set
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * This method is used to get the ModelId of the object Model
     * @return ModelId of the model
     */
    public Integer getModelId() {
        return ModelId;
    }

    /**
     * This method is used to get the Name of the object Model
     * @return Name of the model
     */
    public String getName() {
        return Name;
    }

    /**
     * This method is used to get the Version of the object Model
     * @return Version of the model
     */
    public String getVersion() {
        return Version;
    }

    /**
     * This method is used to get the HDFS Path of the object Model
     * @return HDFS Path of the model
     */
    public String getHDFS_Path() {
        return HDFS_Path;
    }

    /**
     * This method is used to get the Type of the object Model
     * @return Type of the model
     */
    public String getType() {
        return Type;
    }

    /**
     * This is a constructor with two parameters name and version
     * @param name Name of the model created
     * @param version Version of the model created
     */
    public Model(String name, String version) {
        Name = name;
        Version = version;
    }

    /**
     * This is a constructor with five parameters modelId, name, version, HDFS_Path, Type
     * @param modelId modelId of the model created
     * @param name Name of the model created
     * @param version Version of the model created
     * @param HDFS_Path HDFS Path of the model created
     * @param type Type of the model created
     */
    public Model(Integer modelId, String name, String version, String HDFS_Path, String type) {
        ModelId = modelId;
        Name = name;
        Version = version;
        this.HDFS_Path = HDFS_Path;
        Type = type;
    }

    /**
     * This is an empty constructor with no arguments
     */
    public Model() {
    }
}
