package ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tco on 16-04-23.
 */

public class Sky {

    private String skyCover;
    private Integer cloudBaseFtAgl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The skyCover
     */
    public String getSkyCover() {
        return skyCover;
    }

    /**
     *
     * @param skyCover
     * The sky_cover
     */
    public void setSkyCover(String skyCover) {
        this.skyCover = skyCover;
    }

    /**
     *
     * @return
     * The cloudBaseFtAgl
     */
    public Integer getCloudBaseFtAgl() {
        return cloudBaseFtAgl;
    }

    /**
     *
     * @param cloudBaseFtAgl
     * The cloud_base_ft_agl
     */
    public void setCloudBaseFtAgl(Integer cloudBaseFtAgl) {
        this.cloudBaseFtAgl = cloudBaseFtAgl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}