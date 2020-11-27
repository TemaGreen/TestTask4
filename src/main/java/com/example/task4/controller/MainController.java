package com.example.task4.controller;

import com.example.task4.dao.DataAccessObject;
import com.example.task4.dao.TableDataDao;
import com.example.task4.dto.TableDataDto;
import com.example.task4.entity.TableData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table")
public class MainController {

    DataAccessObject dao = new TableDataDao();

    @GetMapping("init")
    public List<TableData> addA() {
        dao.deleteAll();
        return dao.init();
    }

    @GetMapping("getAll")
    public List<TableData> getTable() {
        return dao.finaAll();
    } /*restcontroller возращает не view а  обьект*/

    @PostMapping("insert")
    public List<TableData> addRowTable(@RequestBody TableDataDto tableDataDto) {
        dao.insert(new TableData(tableDataDto));
        return dao.finaAll();
    }

    @PutMapping("update/{id}")
    public List<TableData> updateTable(@PathVariable String id, @RequestBody TableDataDto tableDataDto) {
        dao.update(Integer.valueOf(id), new TableData(tableDataDto));
        return dao.finaAll();
    }

    @DeleteMapping("delete/{id}")
    public void deleteRowTable(@PathVariable String id) {
        dao.delete(Integer.valueOf(id));
    }

    @DeleteMapping("deleteAll")
    public void deleteAllRowsTable() {
        dao.deleteAll();
    }
}

/*
fetch('/table/getAll', { method: 'GET', headers: {'Content-Type': 'application/json'}}).then(result => console.log(result))
fetch('/table/insert', { method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({id: 8, date: '2013-03-01', name: 'BG', value: 5000})}).then(result => console.log(result))
fetch('/table/update', { method: 'PUT', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({id: 6, date: '2013-04-23', name: 'ИП Хамидуллин', value: 2000})}).then(result => console.log(result))
fetch('/table/delete', { method: 'DELETE', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({id: 8, date: '2013-04-23', name: 'ИП Хамидуллин', value: 2000})}).then(result => console.log(result))
 */