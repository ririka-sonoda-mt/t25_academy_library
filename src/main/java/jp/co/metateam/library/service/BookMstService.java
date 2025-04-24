package jp.co.metateam.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.micrometer.common.util.StringUtils;
import jp.co.metateam.library.model.BookMst;
import jp.co.metateam.library.model.BookMstDto;
import jp.co.metateam.library.repository.BookMstRepository;

@Service
public class BookMstService {

    private final BookMstRepository bookMstRepository;

    @Autowired
    public BookMstService(BookMstRepository bookMstRepository) {
        this.bookMstRepository = bookMstRepository;
    }
    

    public BookMst selectByIsbn(String isbn) {
        return bookMstRepository.selectByIsbn(isbn);
    }

    // 書籍の在庫を取得するメソッド
    public List<BookMstDto> findAvailableWithStockCount() {
        List<BookMst> books = this.bookMstRepository.findLimitedBook();
        List<BookMstDto> bookMstDtoList = new ArrayList<>();

        for (BookMst book : books) {
            BookMstDto bookMstDto = new BookMstDto();
            bookMstDto.setId(book.getId());
            bookMstDto.setIsbn(book.getIsbn());
            bookMstDto.setTitle(book.getTitle());
            bookMstDtoList.add(bookMstDto);
        }

        return bookMstDtoList;
    }

    // 書籍保存メソッド
    @Transactional
    public void save(BookMstDto bookMstDto) {
        // ISBNがすでに存在していないかをチェック
    

        BookMst bookMst = new BookMst();
        bookMst.setIsbn(bookMstDto.getIsbn());
        bookMst.setTitle(bookMstDto.getTitle());

        // 書籍情報を保存
        this.bookMstRepository.save(bookMst);
    }

    
    
}


