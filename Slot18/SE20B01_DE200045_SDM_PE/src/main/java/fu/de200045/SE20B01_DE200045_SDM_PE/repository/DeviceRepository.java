package fu.de200045.SE20B01_DE200045_SDM_PE.repository;

import fu.de200045.SE20B01_DE200045_SDM_PE.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findAllByOrderByInstallationDateDesc();

    List<Device> findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(String deviceName);

    Optional<Device> findByDeviceCode(String deviceCode);
}
